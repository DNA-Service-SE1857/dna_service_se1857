package swp_project.dna_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.PostRequest;
import swp_project.dna_service.dto.response.PostResponse;
import swp_project.dna_service.service.PostStatusService;
import java.util.List;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PostStatusController {

    PostStatusService postService;


    @PostMapping()
    public ApiResponse<PostResponse> createPostStatus(@RequestBody PostRequest request) {
        
        var result = postService.createPost(request);
        return ApiResponse.<PostResponse>builder()
                .code(200)
                .result(result)
                .message("Post status created successfully")
                .build();
    }
    @GetMapping("/{userId}")
    public ApiResponse<List<PostResponse>> getPostStatus(@PathVariable String userId) {
        var result = postService.getPostsByUserId(userId);
        return ApiResponse.<List<PostResponse>>builder()
                .code(200)
                .result(result)
                .message("Post status retrieved successfully")
                .build();
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getAllPostStatus() {
        var result = postService.getAllPosts();
        return ApiResponse.<List<PostResponse>>builder()
                .code(200)
                .result(result)
                .message("All post statuses retrieved successfully")
                .build();
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> deletePostStatus(@PathVariable String postId) {
        postService.deletePost(postId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete post status successfully")
                .build();
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> updatePostStatus(@PathVariable String postId, @RequestBody PostRequest request) {
        var result = postService.updatePost(postId, request);
        return ApiResponse.<PostResponse>builder()
                .code(200)
                .result(result)
                .message("Post status updated successfully")
                .build();
    }
    

}