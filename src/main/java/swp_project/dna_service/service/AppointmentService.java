package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.AppointmentRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.entity.DoctorTimeSlot;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.entity.Appointment;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.AppointmentMapper;
import swp_project.dna_service.repository.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AppointmentService {
    AppointmentRepository appointmentRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;
    AppointmentMapper appointmentMapper;
    DoctorTimeSlotRepository doctorTimeSlotRepository;

    // CREATE
    public AppointmentResponse createAppointment(AppointmentRequest request, String orderId ) {
        String userId = extractUserIdFromJwt();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        log.info("Creating appointment for user: {}", userId);

        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        DoctorTimeSlot doctorTimeSlot = doctorTimeSlotRepository.findById(request.getDoctor_time_slot())
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_SLOT_NOT_FOUND));

        Appointment appointment = appointmentMapper.toAppointment(request);
        appointment.setUser(user);
        appointment.setOrders(orders);
        appointment.setDoctorTimeSlot(doctorTimeSlot);

        Date now = new Date();
        appointment.setAppointment_date(now);
        appointment.setCreatedAt(now);
        appointment.setUpdatedAt(now);

        Appointment saved = appointmentRepository.save(appointment);
        AppointmentResponse response = appointmentMapper.toUserResponse(saved);

        response.setId(saved.getId());
        response.setUserId(user.getId());
        response.setOrderId(orders.getId());
        response.setDoctor_time_slot(saved.getDoctorTimeSlot().getId());

        log.info("Appointment created successfully with ID: {}", saved.getId());
        return response;
    }

    // READ by ID
    public AppointmentResponse getById(String id) {
        log.info("Fetching appointment with ID: {}", id);
        
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Appointment not found with ID: {}", id);
                    return new AppException(ErrorCode.APPOINTMENT_NOT_FOUND);
                });
                
        AppointmentResponse response = appointmentMapper.toUserResponse(appointment);
        response.setId(appointment.getId());
        response.setUserId(appointment.getUser().getId());
        response.setOrderId(appointment.getOrders().getId());
        response.setDoctor_time_slot(appointment.getDoctorTimeSlot().getId());
        
        log.info("Successfully retrieved appointment with ID: {}", id);
        return response;
    }

    // READ all by user
    public List<AppointmentResponse> getAllByUser() {
        String userId = extractUserIdFromJwt();
        log.info("Fetching all appointments for user: {}", userId);
        
        List<Appointment> appointments = appointmentRepository.findByUserId(userId);
        List<AppointmentResponse> responses = appointments.stream()
                .map(map -> {
                    AppointmentResponse response = appointmentMapper.toUserResponse(map);
                    response.setId(map.getId());
                    response.setUserId(map.getUser().getId());
                    response.setOrderId(map.getOrders().getId());
                    response.setDoctor_time_slot(map .getDoctorTimeSlot().getId());
                    return response;
                })
                .collect(Collectors.toList());
                
        log.info("Found {} appointments for user: {}", responses.size(), userId);
        return responses;
    }

    public List<AppointmentResponse> getAllAppointments() {
        log.info("Fetching all appointments");

        List<Appointment> appointments = appointmentRepository.findAll();

        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> {
                    AppointmentResponse response = appointmentMapper.toUserResponse(appointment);
                    response.setId(appointment.getId());
                    response.setUserId(appointment.getUser().getId());
                    response.setOrderId(appointment.getOrders().getId());
                    response.setDoctor_time_slot(appointment.getDoctorTimeSlot().getId());

                    response.setCreatedAt(appointment.getCreatedAt());
                    response.setUpdatedAt(appointment.getUpdatedAt());

                    return response;
                })
                .collect(Collectors.toList());

        log.info("Found total {} appointments", responses.size());
        return responses;
    }


    // UPDATE
    public AppointmentResponse updateAppointment(String id, AppointmentRequest request) {
        log.info("Updating appointment with ID: {}", id);
        
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Appointment not found with ID: {}", id);
                    return new AppException(ErrorCode.APPOINTMENT_NOT_FOUND);
                });

        appointmentMapper.updateAppointment(appointment, request);
        appointment.setUpdatedAt(new Date());

        Appointment updated = appointmentRepository.save(appointment);
        AppointmentResponse response = appointmentMapper.toUserResponse(updated);
        
        log.info("Successfully updated appointment with ID: {}", id);
        return response;
    }

    // DELETE
    public void deleteAppointment(String id) {
        log.info("Deleting appointment with ID: {}", id);
        
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Appointment not found with ID: {}", id);
                    return new AppException(ErrorCode.APPOINTMENT_NOT_FOUND);
                });
                
        appointmentRepository.delete(appointment);
        log.info("Successfully deleted appointment with ID: {}", id);
    }


    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}