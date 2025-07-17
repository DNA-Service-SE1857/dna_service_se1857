package swp_project.dna_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.TestResultRequest;
import swp_project.dna_service.dto.response.TestResultResponse;
import swp_project.dna_service.service.TestResultService;

import java.util.List;

@RestController
@RequestMapping("/test-results")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class TestResultController {

    TestResultService testResultService;


    @PostMapping
    public ApiResponse<TestResultResponse> createTestResult(@RequestBody TestResultRequest request) {
        log.info("Received create test result request: {}", request);
        TestResultResponse response = testResultService.createTestResult(request);
        return ApiResponse.<TestResultResponse>builder()
                .code(200)
                .message("Kết quả xét nghiệm đã được tạo thành công")
                .result(response)
                .build();
    }


    @GetMapping
    public ApiResponse<List<TestResultResponse>> getAllTestResults() {
        log.info("Received get all test results request");
        List<TestResultResponse> responses = testResultService.getAllTestResults();
        return ApiResponse.<List<TestResultResponse>>builder()
                .code(200)
                .message("Lấy danh sách kết quả xét nghiệm thành công")
                .result(responses)
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<TestResultResponse> getTestResultById(@PathVariable String id) {
        log.info("Received get test result request for ID: {}", id);
        TestResultResponse response = testResultService.getTestResultById(id);
        return ApiResponse.<TestResultResponse>builder()
                .code(200)
                .message("Lấy kết quả xét nghiệm thành công")
                .result(response)
                .build();
    }


    @GetMapping("/sample/{sampleId}")
    public ApiResponse<List<TestResultResponse>> getTestResultsBySampleId(@PathVariable String sampleId) {
        log.info("Received get test results request for sample ID: {}", sampleId);
        List<TestResultResponse> responses = testResultService.getTestResultsBySampleId(sampleId);
        return ApiResponse.<List<TestResultResponse>>builder()
                .code(200)
                .message("Lấy kết quả xét nghiệm theo mẫu thành công")
                .result(responses)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<TestResultResponse>> getTestResultsByOrderId(@PathVariable String orderId) {
        log.info("Received get test results request for order ID: {}", orderId);
        List<TestResultResponse> responses = testResultService.getTestResultsByOrderId(orderId);
        return ApiResponse.<List<TestResultResponse>>builder()
                .code(200)
                .message("Lấy kết quả xét nghiệm theo đơn hàng thành công")
                .result(responses)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<TestResultResponse>> getTestResultsByUserId(@PathVariable String userId) {
        log.info("Received get test results request for user ID: {}", userId);

        List<TestResultResponse> responses = testResultService.getTestResultsByUserId(userId);
        return ApiResponse.<List<TestResultResponse>>builder()
                .code(200)
                .message("Lấy kết quả xét nghiệm theo người dùng thành công")
                .result(responses)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TestResultResponse> updateTestResult(
            @PathVariable String id,
            @RequestBody TestResultRequest request) {
        log.info("Received update test result request for ID: {}, request: {}", id, request);
        TestResultResponse response = testResultService.updateTestResult(id, request);
        return ApiResponse.<TestResultResponse>builder()
                .code(200)
                .message("Cập nhật kết quả xét nghiệm thành công")
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTestResult(@PathVariable String id) {
        log.info("Received delete test result request for ID: {}", id);
        testResultService.deleteTestResult(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa kết quả xét nghiệm thành công")
                .build();
    }

}
