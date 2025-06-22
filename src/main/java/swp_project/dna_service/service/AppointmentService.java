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
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    // CREATE
    public AppointmentResponse createAppointment(AppointmentRequest request, String serviceId) {
        String userId = getUserIdFromSecurityContext();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Dna_Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        Appointment appointment = appointmentMapper.toAppointment(request);
        appointment.setUser(user);
        appointment.setDna_service(service);

        Date now = new Date();
        appointment.setAppointment_date(now);
        appointment.setCreatedAt(now);
        appointment.setUpdatedAt(now);

        Appointment saved = appointmentRepository.save(appointment);
        AppointmentResponse response = appointmentMapper.toUserResponse(saved);
        response.setId(saved.getId());
        return response;
    }

    // READ by ID
    public AppointmentResponse getById(String id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
        return appointmentMapper.toUserResponse(appointment);
    }

    // READ all by user
    public List<AppointmentResponse> getAllByUser() {
        String userId = getUserIdFromSecurityContext();
        return appointmentRepository.findByUserId(userId).stream()
                .map(appointmentMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    // UPDATE
    public AppointmentResponse updateAppointment(String id, AppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));

        appointmentMapper.updateAppointment(appointment, request); // ✅ gọi đúng cú pháp
        appointment.setUpdatedAt(new Date());

        Appointment updated = appointmentRepository.save(appointment);
        return appointmentMapper.toUserResponse(updated);
    }


    // DELETE
    public void deleteAppointment(String id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.APPOINTMENT_NOT_FOUND));
        appointmentRepository.delete(appointment);
    }


    private String getUserIdFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((Jwt) authentication.getPrincipal()).getClaimAsString("userId");
    }
}