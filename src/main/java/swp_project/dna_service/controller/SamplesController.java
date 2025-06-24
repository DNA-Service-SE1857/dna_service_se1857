package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.SamplesRequest;
import swp_project.dna_service.dto.response.SamplesResponse;
import swp_project.dna_service.service.SamplesService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SamplesController {

    SamplesService samplesService;

    // CREATE
    @PostMapping
    public ApiResponse<SamplesResponse> createSample(@RequestBody SamplesRequest request) {
        log.info("Received create sample request: {}", request);
        SamplesResponse response = samplesService.createSample(request);
        return ApiResponse.<SamplesResponse>builder()
                .code(200)
                .message("Mẫu xét nghiệm đã được tạo thành công")
                .result(response)
                .build();
    }

    // READ
    @GetMapping("/{id}")
    public ApiResponse<SamplesResponse> getSampleById(@PathVariable String id) {
        log.info("Received get sample request for ID: {}", id);
        SamplesResponse response = samplesService.getSampleById(id);
        return ApiResponse.<SamplesResponse>builder()
                .code(200)
                .message("Lấy thông tin mẫu xét nghiệm thành công")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<SamplesResponse>> getAllSamples() {
        log.info("Received get all samples request");
        List<SamplesResponse> responses = samplesService.getAllSamples();
        return ApiResponse.<List<SamplesResponse>>builder()
                .code(200)
                .message("Lấy danh sách mẫu xét nghiệm thành công")
                .result(responses)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<SamplesResponse>> getSamplesByUserId(@PathVariable String userId) {
        log.info("Received get samples request for user ID: {}", userId);
        List<SamplesResponse> responses = samplesService.getSamplesByUserId(userId);
        return ApiResponse.<List<SamplesResponse>>builder()
                .code(200)
                .message("Lấy danh sách mẫu xét nghiệm theo người dùng thành công")
                .result(responses)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<SamplesResponse>> getSamplesByOrderId(@PathVariable String orderId) {
        log.info("Received get samples request for order ID: {}", orderId);
        List<SamplesResponse> responses = samplesService.getSamplesByOrderId(orderId);
        return ApiResponse.<List<SamplesResponse>>builder()
                .code(200)
                .message("Lấy danh sách mẫu xét nghiệm theo đơn hàng thành công")
                .result(responses)
                .build();
    }

    // UPDATE
    @PutMapping("/{id}")
    public ApiResponse<SamplesResponse> updateSample(
            @PathVariable String id,
            @RequestBody SamplesRequest request) {
        log.info("Received update sample request for ID: {}, request: {}", id, request);
        SamplesResponse response = samplesService.updateSample(id, request);
        return ApiResponse.<SamplesResponse>builder()
                .code(200)
                .message("Cập nhật mẫu xét nghiệm thành công")
                .result(response)
                .build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSample(@PathVariable String id) {
        log.info("Received delete sample request for ID: {}", id);
        samplesService.deleteSample(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa mẫu xét nghiệm thành công")
                .build();
    }
}