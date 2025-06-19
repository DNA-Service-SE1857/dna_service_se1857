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
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.DoctorMapper;
import swp_project.dna_service.repository.DoctorRepository;
import swp_project.dna_service.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorService {

    DoctorRepository doctorRepository;
    DoctorMapper doctorMapper;
    UserRepository userRepository;

    public DoctorResponse createDoctor(DoctorRequest request) {
        log.info("Creating doctor with request: {}", request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new AppException(ErrorCode.USER_NOT_FOUND);
                });
        try {
            var doctor = doctorMapper.toDoctor(request);
            doctor.setUser(user);
            doctor.setCreatedAt(new java.util.Date());
            doctor.setUpdatedAt(new java.util.Date());
            doctor = doctorRepository.save(doctor);
            log.info("Doctor created successfully with ID: {}", doctor.getId());

            DoctorResponse response = doctorMapper.toResponse(doctor);
            response.setUserId(userId);
            response.setDoctorId(doctor.getId());
            log.info("Returning doctor response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error creating service: {}", e.getMessage());
            throw new AppException(ErrorCode.SERVICE_CREATION_FAILED);
        }

    }

}
