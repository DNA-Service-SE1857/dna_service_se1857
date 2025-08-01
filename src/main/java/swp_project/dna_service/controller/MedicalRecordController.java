package swp_project.dna_service.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.MedicalRecordRequest;
import swp_project.dna_service.dto.response.MedicalRecordResponse;
import swp_project.dna_service.service.MedicalRecordService;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MedicalRecordController {

    MedicalRecordService medicalRecordService;


    @PostMapping
    public ApiResponse<MedicalRecordResponse> createRecord(@RequestBody MedicalRecordRequest request , @PathVariable String userId) {
        MedicalRecordResponse response = medicalRecordService.createRecord(request , userId );
        return ApiResponse.<MedicalRecordResponse>builder()
                .code(200)
                .message("Medical record created successfully")
                .result(response)
                .build();
    }

    @PostMapping("/{userId}")
    public ApiResponse<MedicalRecordResponse> createRecordByUserID(@RequestBody MedicalRecordRequest request , @PathVariable String userId) {
        MedicalRecordResponse response = medicalRecordService.createRecordByUserId(request , userId);
        return ApiResponse.<MedicalRecordResponse>builder()
                .code(200)
                .message("Medical record created successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MedicalRecordResponse> getById(@PathVariable String id) {
        MedicalRecordResponse response = medicalRecordService.getById(id);
        return ApiResponse.<MedicalRecordResponse>builder()
                .code(200)
                .message("Medical record fetched successfully")
                .result(response)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<MedicalRecordResponse>> getByUserId(@PathVariable String userId) {
        List<MedicalRecordResponse> response = medicalRecordService.getByUserIdByPatch(userId);
        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .code(200)
                .message("Medical record fetched successfully")
                .result(response)
                .build();
    }

//    @GetMapping
//    public ApiResponse<List<MedicalRecordResponse>> getAll() {
//        List<MedicalRecordResponse> responses = medicalRecordService.getAll();
//        return ApiResponse.<List<MedicalRecordResponse>>builder()
//                .code(200)
//                .message("All medical records fetched")
//                .result(responses)
//                .build();
//    }

    @GetMapping("/my")
    public ApiResponse<List<MedicalRecordResponse>> getByCurrentUser() {
        List<MedicalRecordResponse> responses = medicalRecordService.getByUserId();
        return ApiResponse.<List<MedicalRecordResponse>>builder()
                .code(200)
                .message("Medical records for current user fetched")
                .result(responses)
                .build();
    }

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

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRecord(@PathVariable String id) {
        medicalRecordService.deleteById(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Medical record deleted successfully")
                .build();
    }
}
