package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.AppointmentRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {

    AppointmentService appointmentService;

    // CREATE
    @PostMapping("/{orderId}")
    public ApiResponse<AppointmentResponse> createAppointment(
            @RequestBody AppointmentRequest request,
            @PathVariable String orderId) {
        AppointmentResponse response = appointmentService.createAppointment(request, orderId);
        log.info("Appointment created successfully");
        return ApiResponse.<AppointmentResponse>builder()
                .code(200)
                .message("Appointment created successfully")
                .result(response)
                .build();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponse> getAppointmentById(@PathVariable String id) {
        AppointmentResponse response = appointmentService.getById(id);
        return ApiResponse.<AppointmentResponse>builder()
                .code(200)
                .message("Fetched appointment successfully")
                .result(response)
                .build();
    }

    // Get all
    @GetMapping("/all")
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> responses = appointmentService.getAllAppointments();
        return ApiResponse.<List<AppointmentResponse>>builder()
                .code(200)
                .message("Fetched all appointments successfully")
                .result(responses)
                .build();
    }

    // GET all by current user
    @GetMapping("/user/all")
    public ApiResponse<List<AppointmentResponse>> getAllAppointmentsByUser() {
        List<AppointmentResponse> responses = appointmentService.getAllByUser();
        return ApiResponse.<List<AppointmentResponse>>builder()
                .code(200)
                .message("Fetched user appointments successfully")
                .result(responses)
                .build();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ApiResponse<AppointmentResponse> updateAppointment(
            @PathVariable String id,
            @RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.updateAppointment(id, request);
        log.info("Appointment updated successfully");
        return ApiResponse.<AppointmentResponse>builder()
                .code(200)
                .message("Appointment updated successfully")
                .result(response)
                .build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAppointment(@PathVariable String id) {
        appointmentService.deleteAppointment(id);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Appointment deleted successfully")
                .result("Deleted")
                .build();
    }
}