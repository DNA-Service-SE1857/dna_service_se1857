package swp_project.dna_service.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.MedicalRecordRequest;
import swp_project.dna_service.dto.response.MedicalRecordResponse;
import swp_project.dna_service.service.MedicalRecordService;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    MedicalRecordService medicalRecordService;

    // ✅ CREATE
    @PostMapping
    public ApiResponse<MedicalRecordResponse> createRecord(@RequestBody @Valid MedicalRecordRequest request) {
        MedicalRecordResponse response = medicalRecordService.createRecord(request);
        return ApiResponse.<MedicalRecordResponse>builder()
                .code(200)
                .message("Medical record created successfully")
                .result(response)
                .build();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<MedicalRecordResponse> getById(@PathVariable String id) {
        MedicalRecordResponse response = medicalRecordService.getById(id);
        return ApiResponse.<MedicalRecordResponse>builder()
                .code(200)
                .message("Medical record fetched successfully")
                .result(response)
                .build();
    }

    // ✅ GET ALL
    @GetMapping
    public ApiResponse<List<MedicalRecordResponse>> getAll() {
        List<MedicalRecordResponse> responses = medicalRecordService.getAll();
        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .code(200)
                .message("All medical records fetched")
                .result(responses)
                .build();
    }

    // ✅ GET BY CURRENT USER
    @GetMapping("/my")
    public ApiResponse<List<MedicalRecordResponse>> getByCurrentUser() {
        List<MedicalRecordResponse> responses = medicalRecordService.getByUserId();
        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .code(200)
                .message("Medical records for current user fetched")
                .result(responses)
                .build();
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ApiResponse<MedicalRecordResponse> updateRecord(
            @PathVariable String id,
            @RequestBody @Valid MedicalRecordRequest request) {
        MedicalRecordResponse response = medicalRecordService.updateRecord(id, request);
        return ApiResponse.<MedicalRecordResponse>builder()
                .code(200)
                .message("Medical record updated successfully")
                .result(response)
                .build();
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRecord(@PathVariable String id) {
        medicalRecordService.deleteById(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Medical record deleted successfully")
                .build();
    }
}
