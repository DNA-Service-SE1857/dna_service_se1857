package swp_project.dna_service.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.AppointmentRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.entity.Dna_Service;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.entity.Appointment;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.AppointmentMapper;
import swp_project.dna_service.repository.AppointmentRepository;
import swp_project.dna_service.repository.ServiceRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentResponse createAppointment(AppointmentRequest request , String serviceId  ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = ((Jwt) authentication.getPrincipal()).getClaimAsString("userId");

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

    try {

        Dna_Service service = serviceRepository.findById(serviceId)
        .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));


        Appointment appointment = appointmentMapper.toAppointment(request);
        appointment.setUser(user);
        appointment.setDna_service(service);

        Date now = new Date();
        appointment.setAppointment_date(now);
        appointment.setCreatedAt(now);
        appointment.setUpdatedAt(now);
        

        Appointment savedAppointment = appointmentRepository.save(appointment);
        AppointmentResponse response = appointmentMapper.toUserResponse(savedAppointment);
        response.setId(savedAppointment.getId());
        response.setServiceId(serviceId);
        response.setUserId(userId);
        
        return response;
    } catch (Exception e) {
        log.error("Error creating appointment: {}", e.getMessage(), e);
        throw new AppException(ErrorCode.SERVICE_CREATION_FAILED);
    }
}
}