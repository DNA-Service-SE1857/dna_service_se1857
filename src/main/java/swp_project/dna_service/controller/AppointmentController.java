package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.AppointmentRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.service.AppointmentService;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {

    AppointmentService appointmentService;

    @PostMapping("/{serviceId}")
    public ApiResponse<AppointmentResponse> createAppointment(
            @RequestBody AppointmentRequest request,
            @PathVariable String serviceId) {
        AppointmentResponse appointmentResponse = appointmentService.createAppointment(request, serviceId);
        log.info("Appointment created successfully");
        return ApiResponse.<AppointmentResponse>builder()
                .code(200)
                .message("Appointment created successfully")
                .result(appointmentResponse)
                .build();
    }
}