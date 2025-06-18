package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.response.ServiceResponse;
import swp_project.dna_service.entity.Dna_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import swp_project.dna_service.dto.request.ServiceRequest;
import swp_project.dna_service.service.DnaService;

import java.util.List;


@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ServiceController {
    
    DnaService service;
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")  
    public ApiResponse<ServiceResponse> createService(@RequestBody ServiceRequest request) {
        log.info("Creating new service: {}", request);
        
        ServiceResponse response = service.createService(request);
        
        return ApiResponse.<ServiceResponse>builder()
                .code(200)
                .message("Service created successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{serviceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<ServiceResponse> updateService(@PathVariable String serviceId, @RequestBody ServiceRequest request) {
        log.info("Updating service with ID: {}", serviceId);

        ServiceResponse response = service.updateService(serviceId, request);

        return ApiResponse.<ServiceResponse>builder()
                .code(200)
                .message("Service updated successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{serviceId}")
    public ApiResponse<ServiceResponse> getService(@PathVariable String serviceId) {
        log.info("Getting service with ID: {}", serviceId);
        ServiceResponse response = service.getServiceById(serviceId);
        return ApiResponse.<ServiceResponse>builder()
                .code(200)
                .message("Service retrieved successfully")
                .result(response)
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<ServiceResponse>> getAllService() {
        log.info("Getting all services");
        List<ServiceResponse> services = service.getAllServices();
        return ApiResponse.<List<ServiceResponse>>builder()
                .code(200)
                .message("All services retrieved successfully")
                .result(services)
                .build();
    }

    @GetMapping("/{userId}/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<List<ServiceResponse>> getAllServicesByUserId(@PathVariable String userId) {
        log.info("Getting all services for user ID: {}", userId);
       var services = service.getServicesByUserId(userId);
        return ApiResponse.<List<ServiceResponse>>builder()
                .code(200)
                .message("All services for user retrieved successfully")
                .result(services)
                .build();
    }


    @DeleteMapping("/{serviceId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<Void> deleteService(@PathVariable String serviceId) {
        log.info("Deleting service with ID: {}", serviceId);
        service.deleteService(serviceId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Service deleted successfully")
                .build();
    }
}