package swp_project.dna_service.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.RoleRequest;
import swp_project.dna_service.dto.response.RoleResponse;
import swp_project.dna_service.service.RoleService;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .message("Role created successfully")
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(200)
                .message("Roles retrieved successfully")
                .result(roleService.getAllRole())
                .build();
    }

    @PutMapping("/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResponse<RoleResponse> updateRole(@PathVariable String role, @RequestBody RoleRequest request) {
        request.setName(role);
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .message("Role updated successfully")
                .result(roleService.createRole(request))
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder().code(200).build();
    }
}