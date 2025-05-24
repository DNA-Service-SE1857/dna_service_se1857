package swp_project.dna_service.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.UserRequest;
import swp_project.dna_service.dto.response.UserResponse;
import swp_project.dna_service.service.UserService;

@RestController
@RequestMapping("/user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(@RequestBody UserRequest user) {
        log.info("Registering user: {}", user.getUsername());

        return ApiResponse.<UserResponse>builder()
                .message("User registered successfully")
                .code(200)
                .result(userService.createUser(user))
                .build();
    }
}
