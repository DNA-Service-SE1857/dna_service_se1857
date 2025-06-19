package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.DoctorRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.dto.response.DoctorResponse;
import swp_project.dna_service.service.DoctorService;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {

    DoctorService doctorService;

    @PostMapping()
    public ApiResponse<DoctorResponse> createDoctor(
            @RequestBody DoctorRequest request) {
        var result = doctorService.createDoctor(request);

        log.info("Appointment created successfully");
        return ApiResponse.<DoctorResponse>builder()
                .code(200)
                .message("Appointment created successfully")
                .result(result)
                .build();
    }
}