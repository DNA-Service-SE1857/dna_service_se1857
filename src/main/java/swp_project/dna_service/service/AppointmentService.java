package swp_project.dna_service.service;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.AppointmentRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.dto.response.ServiceResponse;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.AppointmentMapper;
import swp_project.dna_service.repository.AppointmentRepository;
import swp_project.dna_service.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE , makeFinal = true)
public class AppointmentService {

    AppointmentRepository appointmentRepository;
    AppointmentMapper appointmentMapper;
    UserRepository userRepository ;


    public AppointmentResponse createAppointment(AppointmentRequest request) {
        log.info("Creating post with request: {}", request);
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
            var appointment = appointmentMapper.toAppointment(request);
            appointment.setUser(user);
            appointment.setCreatedAt(new java.util.Date());
            appointment.setUpdatedAt(new java.util.Date());

            appointment = appointmentRepository.save(appointment);
            log.info("Appointment created successfully with ID: {}", appointment.getId());

            AppointmentResponse response = appointmentMapper.toUserResponse(appointment);
            response.setUserId(userId);
            log.info("Returning service response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error creating service: {}", e.getMessage());
            throw new AppException(ErrorCode.SERVICE_CREATION_FAILED);
        }
    }
}
