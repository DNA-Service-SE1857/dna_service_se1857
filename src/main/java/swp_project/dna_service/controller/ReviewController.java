package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.ReviewRequest;
import swp_project.dna_service.dto.response.ReviewResponse;
import swp_project.dna_service.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ReviewController{

    ReviewService reviewService;

    @PostMapping("/{orderId}")
    public ApiResponse<ReviewResponse> createReview(@RequestBody ReviewRequest request , @PathVariable String orderId) {
        ReviewResponse response = reviewService.createReview(request , orderId);
        return ApiResponse.<ReviewResponse>builder()
                .code(200)
                .message("Review created successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ReviewResponse> getReviewById(@PathVariable String id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return ApiResponse.<ReviewResponse>builder()
                .code(200)
                .message("Review retrieved successfully")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<ReviewResponse>> getAllReviews() {
        List<ReviewResponse> responses = reviewService.getAllReviews();
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(200)
                .message("All reviews retrieved successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByUser(@PathVariable String userId) {
        List<ReviewResponse> responses = reviewService.getReviewsByUserId(userId);
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(200)
                .message("Reviews by user retrieved successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<ReviewResponse>> getReviewsByOrder(@PathVariable String orderId) {
        List<ReviewResponse> responses = reviewService.getReviewsByOrderId(orderId);
        return ApiResponse.<List<ReviewResponse>>builder()
                .code(200)
                .message("Reviews by order retrieved successfully")
                .result(responses)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ReviewResponse> updateReview(@PathVariable String id, @RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.updateReview(id, request);
        return ApiResponse.<ReviewResponse>builder()
                .code(200)
                .message("Review updated successfully")
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(id);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Review deleted successfully")
                .result("Deleted review with ID: " + id)
                .build();
    }
}
