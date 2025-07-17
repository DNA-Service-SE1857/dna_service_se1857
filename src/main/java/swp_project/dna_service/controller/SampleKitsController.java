package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.SampleKitsRequest;
import swp_project.dna_service.dto.response.SampleKitsResponse;
import swp_project.dna_service.service.SampleKitsService;

import java.util.List;

@RestController
@RequestMapping("/sample-kits")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SampleKitsController {

    SampleKitsService sampleKitsService;

    @PostMapping
    public ApiResponse<SampleKitsResponse> createSampleKits(@RequestBody SampleKitsRequest request) {
        log.info("Received create sample kit request: {}", request);
        SampleKitsResponse response = sampleKitsService.createSampleKits(request);
        return ApiResponse.<SampleKitsResponse>builder()
                .code(200)
                .message("Sample kits created successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SampleKitsResponse> getSampleKitById(@PathVariable String id) {
        log.info("Received get sample kit request for ID: {}", id);
        SampleKitsResponse response = sampleKitsService.getSampleKitById(id);
        return ApiResponse.<SampleKitsResponse>builder()
                .code(200)
                .message("Sample kit retrieved successfully")
                .result(response)
                .build();
    }

    @GetMapping("/participants/{orderParticipantsId}")
    public ApiResponse<List<SampleKitsResponse>> getSampleKitsByOrderParticipantsId(@PathVariable String orderParticipantsId) {
        log.info("Received get sample kits request for order participants ID: {}", orderParticipantsId);
        List<SampleKitsResponse> responses = sampleKitsService.getSampleKitByParticipantsId(orderParticipantsId);
        return ApiResponse.<List<SampleKitsResponse>>builder()
                .code(200)
                .message("Sample kits retrieved successfully")
                .result(responses)
                .build();
    }

    @GetMapping
    public ApiResponse<List<SampleKitsResponse>> getAllSampleKits() {
        log.info("Received get all sample kits request");
        List<SampleKitsResponse> responses = sampleKitsService.getAllSampleKits();
        return ApiResponse.<List<SampleKitsResponse>>builder()
                .code(200)
                .message("Sample kits retrieved successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/user")
    public ApiResponse<List<SampleKitsResponse>> getSampleKitsByUserId() {
        log.info("Received get sample kits for current user request");
        List<SampleKitsResponse> responses = sampleKitsService.getSampleKitsByUserId();
        return ApiResponse.<List<SampleKitsResponse>>builder()
                .code(200)
                .message("Sample kits retrieved successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/samples/{samplesId}")
    public ApiResponse<List<SampleKitsResponse>> getSampleKitsBySamplesId(@PathVariable String samplesId) {
        log.info("Received get sample kits request for samples ID: {}", samplesId);
        List<SampleKitsResponse> responses = sampleKitsService.getSampleKitsBySamplesId(samplesId);
        return ApiResponse.<List<SampleKitsResponse>>builder()
                .code(200)
                .message("Sample kits retrieved successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<SampleKitsResponse>> getSampleKitsByOrderId(@PathVariable String orderId) {
        log.info("Received get sample kits request for order ID: {}", orderId);
        List<SampleKitsResponse> responses = sampleKitsService.getSampleKitByOrderId(orderId);
        return ApiResponse.<List<SampleKitsResponse>>builder()
                .code(200)
                .message("Sample kits retrieved successfully")
                .result(responses)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SampleKitsResponse> updateSampleKit(
            @PathVariable String id,
            @RequestBody SampleKitsRequest request) {
        log.info("Received update sample kit request for ID: {}, request: {}", id, request);
        SampleKitsResponse response = sampleKitsService.updateSampleKit(id, request);
        return ApiResponse.<SampleKitsResponse>builder()
                .code(200)
                .message("Sample kit updated successfully")
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSampleKit(@PathVariable String id) {
        log.info("Received delete sample kit request for ID: {}", id);
        sampleKitsService.deleteSampleKit(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Sample kit deleted successfully")
                .build();
    }


}
