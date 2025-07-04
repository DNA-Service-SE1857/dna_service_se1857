package swp_project.dna_service.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.SampleKitsRequest;
import swp_project.dna_service.dto.response.SampleKitsResponse;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.entity.Samples;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.SampleKitsMapper;
import swp_project.dna_service.repository.OrderRepository;
import swp_project.dna_service.repository.SampleKitsRepository;
import swp_project.dna_service.repository.SamplesRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import swp_project.dna_service.entity.SampleKits;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class SampleKitsService {
    
    SampleKitsRepository sampleKitsRepository;
    SampleKitsMapper sampleKitsMapper;
    SamplesRepository samplesRepository;
    UserRepository userRepository;
    OrderRepository orderRepository;
    
    // CREATE
    public SampleKitsResponse createSampleKits(SampleKitsRequest request) {
        log.info("Creating sample kit with request: {}", request);
        
        String userId = extractUserIdFromJwt();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                
        Samples samples = samplesRepository.findById(request.getSamplesId())
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_NOT_FOUND));

        Orders order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
                
        var sampleKits = sampleKitsMapper.toSampleKit(request);
        sampleKits.setSamples(samples);
        sampleKits.setUser(user);
        sampleKits.setOrders(order);
        
        Date now = new Date();
        sampleKits.setCreatedAt(now);
        sampleKits.setUpdatedAt(now);
        
        var savedSampleKits = sampleKitsRepository.save(sampleKits);
        var response = sampleKitsMapper.toSampleKitResponse(savedSampleKits);
        
        response.setId(savedSampleKits.getId());
        response.setSamplesId(samples.getId());
        response.setUserId(user.getId());
        response.setOrderId(order.getId());
        
        log.info("Sample kit created successfully with ID: {}", savedSampleKits.getId());
        return response;
    }

    // READ
    public SampleKitsResponse getSampleKitById(String id) {
        log.info("Getting sample kit by ID: {}", id);
        
        var sampleKit = sampleKitsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_KITS_NOT_FOUND));
                
        var response = sampleKitsMapper.toSampleKitResponse(sampleKit);
        response.setId(sampleKit.getId());
        response.setSamplesId(sampleKit.getSamples().getId());
        response.setUserId(sampleKit.getUser().getId());
        
        return response;
    }

    public List<SampleKitsResponse> getSampleKitByOrderId(String orderId) {
        log.info("Getting sample kit by order ID: {}", orderId);

        List<SampleKits> sampleKits = sampleKitsRepository.findByOrders_Id(orderId);
        if (sampleKits.isEmpty()) {
            throw new AppException(ErrorCode.SAMPLE_KITS_NOT_FOUND);
        }

        return sampleKitsRepository.findAll().stream()
                .map(kit -> {
                    var response = sampleKitsMapper.toSampleKitResponse(kit);
                    response.setId(kit.getId());
                    response.setSamplesId(kit.getSamples().getId());
                    response.setUserId(kit.getUser().getId());
                    response.setOrderId(kit.getOrders().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    
    public List<SampleKitsResponse> getAllSampleKits() {
        log.info("Getting all sample kits");
        
        return sampleKitsRepository.findAll().stream()
                .map(sampleKit -> {
                    var response = sampleKitsMapper.toSampleKitResponse(sampleKit);
                    response.setId(sampleKit.getId());
                    response.setSamplesId(sampleKit.getSamples().getId());
                    response.setUserId(sampleKit.getUser().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<SampleKitsResponse> getSampleKitsByUserId() {
        String userId = extractUserIdFromJwt();
        log.info("Getting sample kits for user ID: {}", userId);
        
        return sampleKitsRepository.findByUserId(userId).stream()
                .map(sampleKit -> {
                    var response = sampleKitsMapper.toSampleKitResponse(sampleKit);
                    response.setId(sampleKit.getId());
                    response.setSamplesId(sampleKit.getSamples().getId());
                    response.setUserId(sampleKit.getUser().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<SampleKitsResponse> getSampleKitsBySamplesId(String samplesId) {
        log.info("Getting sample kits by samples ID: {}", samplesId);
        
        List<SampleKits> sampleKits = sampleKitsRepository.findBySamplesId(samplesId);
        
        return sampleKits.stream()
                .map(sampleKit -> {
                    var response = sampleKitsMapper.toSampleKitResponse(sampleKit);
                    response.setId(sampleKit.getId());
                    
                    if (sampleKit.getSamples() != null) {
                        response.setSamplesId(sampleKit.getSamples().getId());
                    }
                    
                    if (sampleKit.getUser() != null) {
                        response.setUserId(sampleKit.getUser().getId());
                    }
                    
                    return response;
                })
                .collect(Collectors.toList());
}


    // UPDATE
    public SampleKitsResponse updateSampleKit(String id, SampleKitsRequest request) {
        log.info("Updating sample kit ID: {} with request: {}", id, request);
        
        var existingSampleKit = sampleKitsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_KITS_NOT_FOUND));
        
        String userId = extractUserIdFromJwt();
        if (!existingSampleKit.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        
        sampleKitsMapper.updateSampleKit(existingSampleKit, request);
        existingSampleKit.setUpdatedAt(new Date());
        
        if (request.getSamplesId() != null) {
            Samples samples = samplesRepository.findById(request.getSamplesId())
                    .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_NOT_FOUND));
            existingSampleKit.setSamples(samples);
        }
        
        var savedSampleKit = sampleKitsRepository.save(existingSampleKit);
        var response = sampleKitsMapper.toSampleKitResponse(savedSampleKit);
        
        response.setId(savedSampleKit.getId());
        response.setSamplesId(savedSampleKit.getSamples().getId());
        response.setUserId(savedSampleKit.getUser().getId());
        
        log.info("Sample kit updated successfully");
        return response;
    }

    // DELETE
    public void deleteSampleKit(String id) {
        log.info("Deleting sample kit with ID: {}", id);
        
        var sampleKit = sampleKitsRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_KITS_NOT_FOUND));
        
        String userId = extractUserIdFromJwt();
        if (!sampleKit.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        
        sampleKitsRepository.delete(sampleKit);
        log.info("Sample kit deleted successfully");
    }

    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}