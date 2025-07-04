
package swp_project.dna_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.DoctorRequest;
import swp_project.dna_service.dto.response.DoctorResponse;
import swp_project.dna_service.service.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    public ApiResponse<DoctorResponse> createDoctor(@RequestBody DoctorRequest request) {
        DoctorResponse response = doctorService.createDoctor(request);
        return ApiResponse.<DoctorResponse>builder()
                .code(200)
                .message("Doctor created successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{doctorId}")
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_ADMIN')")
    public ApiResponse<DoctorResponse> updateDoctor(
            @PathVariable String doctorId,
            @RequestBody @jakarta.validation.Valid DoctorRequest request) {
        DoctorResponse response = doctorService.updateDoctor(doctorId, request);
        return ApiResponse.<DoctorResponse>builder()
                .code(200)
                .message("Doctor updated successfully")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<DoctorResponse>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.getAllDoctors();
        return ApiResponse.<List<DoctorResponse>>builder()
                .code(200)
                .message("Get all doctors successfully")
                .result(doctors)
                .build();
    }

    @GetMapping("/{doctorId}")
    public ApiResponse<DoctorResponse> getDoctor(@PathVariable String doctorId) {
        DoctorResponse response = doctorService.getDoctorById(doctorId);
        return ApiResponse.<DoctorResponse>builder()
                .code(200)
                .message("Doctor retrieved successfully")
                .result(response)
                .build();
    }

    @DeleteMapping("/{doctorId}")
    public ApiResponse<Object> deleteDoctor(@PathVariable String doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ApiResponse.builder()
                .code(200)
                .message("Doctor deleted successfully")
                .build();
    }
}
