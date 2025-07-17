package swp_project.dna_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.DoctorCertificateRequest;
import swp_project.dna_service.dto.response.DoctorCertificateResponse;
import swp_project.dna_service.service.DoctorCertificateService;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class DoctorCertificateController {

    private final DoctorCertificateService doctorCertificateService;

    @PostMapping
    public ApiResponse<DoctorCertificateResponse> createCertificate(
            @RequestBody @Valid DoctorCertificateRequest request) {

        DoctorCertificateResponse response = doctorCertificateService.createCertificate(request);
        return ApiResponse.<DoctorCertificateResponse>builder()
                .code(200)
                .message("Certificate created successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{certificateId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<DoctorCertificateResponse> updateCertificate(
            @PathVariable String certificateId,
            @RequestBody @Valid DoctorCertificateRequest request) {

        DoctorCertificateResponse response = doctorCertificateService.updateCertificate(certificateId, request);
        return ApiResponse.<DoctorCertificateResponse>builder()
                .code(200)
                .message("Certificate updated successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{certificateId}")
    public ApiResponse<DoctorCertificateResponse> getCertificateById(@PathVariable String certificateId) {
        DoctorCertificateResponse response = doctorCertificateService.getCertificateById(certificateId);
        return ApiResponse.<DoctorCertificateResponse>builder()
                .code(200)
                .message("Certificate retrieved successfully")
                .result(response)
                .build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<List<DoctorCertificateResponse>> getCertificatesByDoctorId(@PathVariable String doctorId) {
        List<DoctorCertificateResponse> list = doctorCertificateService.getCertificatesByDoctorId(doctorId);
        return ApiResponse.<List<DoctorCertificateResponse>>builder()
                .code(200)
                .message("Certificates retrieved successfully")
                .result(list)
                .build();
    }

    @GetMapping
    public ApiResponse<List<DoctorCertificateResponse>> getAllCertificates() {
        List<DoctorCertificateResponse> list = doctorCertificateService.getAllCertificates();
        return ApiResponse.<List<DoctorCertificateResponse>>builder()
                .code(200)
                .message("All certificates retrieved successfully")
                .result(list)
                .build();
    }

    @DeleteMapping("/{certificateId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Object> deleteCertificate(@PathVariable String certificateId) {
        doctorCertificateService.deleteCertificate(certificateId);
        return ApiResponse.builder()
                .code(200)
                .message("Certificate deleted successfully")
                .build();
    }
}
