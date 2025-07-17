package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.ServiceRequest;
import swp_project.dna_service.dto.response.ServiceResponse;
import swp_project.dna_service.entity.Dna_Service;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.ServiceMapper;
import swp_project.dna_service.repository.ServiceRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DnaService {


    ServiceRepository serviceRepository;
    UserRepository userRepository;
    ServiceMapper serviceMapper;


    public ServiceResponse createService( ServiceRequest service) {
        log.info("Creating post with request: {}", service);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new AppException(ErrorCode.USER_NOT_FOUND);
                });

        try {
            var dnaService = serviceMapper.toService(service);
            dnaService.setUser(user);
            dnaService.setCreatedAt(new java.util.Date());
            dnaService.setUpdatedAt(new java.util.Date());

            dnaService = serviceRepository.save(dnaService);
            log.info("Service created successfully with ID: {}", dnaService.getId());

            ServiceResponse response = serviceMapper.toServiceResponse(dnaService);
            response.setUserId(userId);
            log.info("Returning service response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error creating service: {}", e.getMessage());
            throw new AppException(ErrorCode.SERVICE_CREATION_FAILED);
        }
    }

    public ServiceResponse updateService(String serviceId, ServiceRequest service) {
        log.info("Updating service with ID: {}", serviceId);
        var existingService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        try {
            serviceMapper.updateService(existingService, service);
            existingService.setUpdatedAt(new java.util.Date());
            existingService = serviceRepository.save(existingService);
            log.info("Service updated successfully with ID: {}", existingService.getId());

            ServiceResponse response = serviceMapper.toServiceResponse(existingService);
            log.info("Returning updated service response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error updating service: {}", e.getMessage());
            throw new AppException(ErrorCode.SERVICE_UPDATE_FAILED);
        }
    }

    public ServiceResponse getServiceById(String serviceId) {
        log.info("Fetching service with ID: {}", serviceId);
        var service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        log.info("Service found: {}", service);
        return serviceMapper.toServiceResponse(service);
    }

    public List<ServiceResponse> getServicesByUserId(String userId) {
        log.info("Fetching services for user ID: {}", userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        List<Dna_Service> services = serviceRepository.findByUserId(userId);

        log.info("Found {} services for user ID: {}", services.size(), userId);

        return services.stream()
                .map(serviceMapper::toServiceResponse)
                .toList();
    }

    public List<ServiceResponse> getAllServices() {
        log.info("Fetching all services");
        var services = serviceRepository.findAll();
        log.info("Found {} services", services.size());
        return services.stream().map(serviceMapper::toServiceResponse).toList();
    }

    public void deleteService(String serviceId) {
        log.info("Deleting service with ID: {}", serviceId);
        var service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        serviceRepository.delete(service);
        log.info("Service with ID: {} deleted successfully", serviceId);
    }
}
