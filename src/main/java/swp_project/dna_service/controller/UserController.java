package swp_project.dna_service.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.UserCreationRequest;
import swp_project.dna_service.dto.request.UserUpdateRequest;
import swp_project.dna_service.dto.response.UserResponse;
import swp_project.dna_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(@RequestBody @Valid UserCreationRequest user) {
        log.info("Registering user: {}", user.getUsername());

        return ApiResponse.<UserResponse>builder()
                .message("User registered successfully")
                .code(200)
                .result(userService.createUser(user))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String id) {
        log.info("Getting user by id: {}", id);
        UserResponse userResponse = userService.getUserById(id);
        return ApiResponse.<UserResponse>builder()
                .message("User retrieved successfully")
                .code(200)
                .result(userResponse)
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<UserResponse> getUserProfile() {
        log.info("Getting user profile for current user");
        UserResponse userResponse = userService.getProfile();
        return ApiResponse.<UserResponse>builder()
                .message("User profile retrieved successfully")
                .code(200)
                .result(userResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUser() {
        log.info("Getting all users");
        List<UserResponse> userResponses = userService.getAllUser();
        return ApiResponse.<List<UserResponse>>builder()
                .message("All users retrieved successfully")
                .code(200)
                .result(userResponses)
                .build();
    }

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String userId , @RequestBody @Valid UserUpdateRequest user) {
        log.info("Updating user: {}", user.getUsername());
        UserResponse updatedUser = userService.updateUser(userId ,user);
        return ApiResponse.<UserResponse>builder()
                .message("User updated successfully")
                .code(200)
                .result(updatedUser)
                .build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteUser(@PathVariable String userId) {
        log.info("Deleting user: {}", userId);
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .message("User deleted successfully")
                .code(200)
                .result("User with ID " + userId + " has been deleted.")
                .build();
    }
}
