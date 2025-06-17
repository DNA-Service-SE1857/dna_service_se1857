package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.response.ServiceResponse;
import swp_project.dna_service.entity.Dna_Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import swp_project.dna_service.dto.request.ServiceRequest;
import swp_project.dna_service.service.DnaService;


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
}