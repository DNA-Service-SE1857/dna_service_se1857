package swp_project.dna_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.DoctorRequest;
import swp_project.dna_service.dto.response.DoctorResponse;
import swp_project.dna_service.entity.Doctor;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.DoctorMapper;
import swp_project.dna_service.repository.DoctorRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorService {

    DoctorRepository doctorRepository;
    UserRepository userRepository;
    DoctorMapper doctorMapper;

    public DoctorResponse createDoctor(DoctorRequest request) {
        log.info("Creating doctor with request: {}", request);

        String userId = extractUserIdFromJwt();
        User user = getUserById(userId);

        Doctor doctor = doctorMapper.toDoctor(request);
        doctor.setUser(user);
        Date now = new Date();
        doctor.setCreatedAt(now);
        doctor.setUpdatedAt(now);

        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Created doctor with ID: {}", savedDoctor.getId());

        DoctorResponse response = doctorMapper.toDoctorResponse(savedDoctor);
        response.setDoctorId(savedDoctor.getId());
        response.setUserId(user.getId());

        return response;
    }

    public DoctorResponse updateDoctor(String doctorId, DoctorRequest request) {
        log.info("Updating doctor with ID: {}", doctorId);

        String userId = extractUserIdFromJwt();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", doctorId);
                    return new AppException(ErrorCode.DOCTOR_NOT_FOUND);
                });

        // Check permission: owner or admin
        checkDoctorAccessPermission(doctor, userId);

        doctorMapper.updateDoctor(doctor, request);
        doctor.setUpdatedAt(new Date());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        log.info("Updated doctor with ID: {}", updatedDoctor.getId());

        return doctorMapper.toDoctorResponse(updatedDoctor);
    }

    public DoctorResponse getDoctorById(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", doctorId);
                    return new AppException(ErrorCode.DOCTOR_NOT_FOUND);
                });

        DoctorResponse response = doctorMapper.toDoctorResponse(doctor);
        response.setDoctorId(doctor.getId());
        response.setUserId(doctor.getUser().getId());
        return response;
    }

    public List<DoctorResponse> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();

        return doctors.stream()
                .map(doctor -> {
                    DoctorResponse response = doctorMapper.toDoctorResponse(doctor);
                    response.setDoctorId(doctor.getId());
                    response.setUserId(doctor.getUser().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }


    public void deleteDoctor(String doctorId) {
        log.info("Deleting doctor with ID: {}", doctorId);

        String userId = extractUserIdFromJwt();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", doctorId);
                    return new AppException(ErrorCode.DOCTOR_NOT_FOUND);
                });

        // Check permission: owner or admin
        checkDoctorAccessPermission(doctor, userId);

        doctorRepository.delete(doctor);
        log.info("Deleted doctor with ID: {}", doctorId);
    }

    // ====== Helper methods ======

    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    private User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    private void checkDoctorAccessPermission(Doctor doctor, String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isOwner = doctor.getUser().getId().equals(userId);
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN") || auth.getAuthority().equals("ROLE_STAFF"));

        if (!isOwner && !isAdmin) {
            log.error("User {} is not authorized to modify doctor {}", userId, doctor.getId());
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }
    }
}
