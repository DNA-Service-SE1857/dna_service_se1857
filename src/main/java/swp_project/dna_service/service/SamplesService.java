package swp_project.dna_service.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.SampleKitsRequest;
import swp_project.dna_service.dto.request.SamplesRequest;
import swp_project.dna_service.dto.response.SampleKitsResponse;
import swp_project.dna_service.dto.response.SamplesResponse;
import swp_project.dna_service.entity.*;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.SamplesMapper;
import swp_project.dna_service.repository.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class SamplesService {

    SamplesRepository samplesRepository;
    SamplesMapper samplesMapper;
    UserRepository userRepository;
    TestResultRepository testResultRepository;
    OrderRepository orderRepository;
    SampleKitsRepository sampleKitsRepository;


    public SamplesResponse createSample(SamplesRequest request) {
        log.info("Creating sample with request: {}", request);

        String userId = extractUserIdFromJwt();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Orders orders = orderRepository.findById(request.getOrdersId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        SampleKits sampleKits = sampleKitsRepository.findById(request.getSampleKitsId())
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_KITS_NOT_FOUND));

        var sample = samplesMapper.toSamples(request);
        Date now = new Date();
        sample.setCreatedAt(now);
        sample.setUpdatedAt(now);

        sample.setUser(user);
        sample.setSampleKits(sampleKits);
        sample.setOrders(orders);

        var savedSample = samplesRepository.save(sample);

        var response = samplesMapper.toSamplesResponse(savedSample);

        response.setUserId(savedSample.getUser().getId());
        response.setSampleKitsId(savedSample.getSampleKits().getId());
        response.setOrdersId(savedSample.getOrders().getId());

        log.info("Sample created successfully: {}", response);
        return response;
    }


    @Transactional
    public List<SamplesResponse> getSamplesByTestResult(String testResultId) {
        log.info("Getting samples for test result ID: {}", testResultId);

        testResultRepository.findById(testResultId)
                .orElseThrow(() -> new AppException(ErrorCode.TEST_RESULT_NOT_FOUND));

        return samplesRepository.findByTestResultId(testResultId).stream()
                .map(sample -> {
                    var response = samplesMapper.toSamplesResponse(sample);
                    response.setUserId(sample.getUser().getId());
                    response.setSampleKitsId(sample.getSampleKits().getId());
                    response.setOrdersId(sample.getOrders().getId());
                    response.setTestResultId(sample.getTestResult().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SamplesResponse> getSamplesByOrderId(String orderId) {
        log.info("Getting samples for order ID: {}", orderId);

        orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        return samplesRepository.findByOrdersId(orderId).stream()
                .map(sample -> {
                    var response = samplesMapper.toSamplesResponse(sample);

                    if (sample.getUser() != null) {
                        response.setUserId(sample.getUser().getId());
                    }

                    if (sample.getSampleKits() != null) {
                        response.setSampleKitsId(sample.getSampleKits().getId());
                    }

                    if (sample.getOrders() != null) {
                        response.setOrdersId(sample.getOrders().getId());
                    }

                    if (sample.getTestResult() != null) {
                        response.setTestResultId(sample.getTestResult().getId());
                    } else {
                        response.setTestResultId(null);
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public SamplesResponse getSampleById(String id) {
        log.info("Getting sample by ID: {}", id);
        
        var sample = samplesRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_NOT_FOUND));
            
        var response = samplesMapper.toSamplesResponse(sample);
        response.setUserId(sample.getUser().getId());
        response.setSampleKitsId(sample.getSampleKits().getId());
        response.setOrdersId(sample.getOrders().getId());
        response.setTestResultId(sample.getTestResult().getId());
        return response;
    }

    @Transactional
    public List<SamplesResponse> getAllSamples() {
        log.info("Getting all samples");

        return samplesRepository.findAll().stream()
                .map(sample -> {
                    var response = samplesMapper.toSamplesResponse(sample);

                    if (sample.getUser() != null) {
                        response.setUserId(sample.getUser().getId());
                    }

                    if (sample.getSampleKits() != null) {
                        response.setSampleKitsId(sample.getSampleKits().getId());
                    }

                    if (sample.getOrders() != null) {
                        response.setOrdersId(sample.getOrders().getId());
                    }

                    if (sample.getTestResult() != null) {
                        response.setTestResultId(sample.getTestResult().getId());
                    } else {
                        response.setTestResultId(null); // optional, an explicit null
                    }

                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SamplesResponse> getSamplesBySampleKitId(String sampleKitId) {
        log.info("Getting samples for sample kit ID: {}", sampleKitId);

        sampleKitsRepository.findById(sampleKitId)
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_KITS_NOT_FOUND));

        return samplesRepository.findBySampleKitsId(sampleKitId).stream()
                .map(sample -> {
                    var response = samplesMapper.toSamplesResponse(sample);
                    response.setUserId(sample.getUser().getId());
                    response.setSampleKitsId(sample.getSampleKits().getId());
                    response.setOrdersId(sample.getOrders().getId());

                    if (sample.getTestResult() != null) {
                        response.setTestResultId(sample.getTestResult().getId().toString());
                    } else {
                        response.setTestResultId(null);
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public List<SamplesResponse> getSamplesByUserId(String userId) {
        log.info("Getting samples for user ID: {}", userId);

        userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return samplesRepository.findByUserId(userId).stream()
                .map(sample -> {
                    var response = samplesMapper.toSamplesResponse(sample);
                    response.setUserId(sample.getUser().getId());
                    response.setSampleKitsId(sample.getSampleKits().getId());
                    response.setOrdersId(sample.getOrders().getId());
                    response.setTestResultId(sample.getTestResult().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public SamplesResponse updateSample(String id, SamplesRequest request) {
        log.info("Updating sample ID: {} with request: {}", id, request);

        var existingSample = samplesRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_NOT_FOUND));

        if (request.getOrdersId() != null) {
            Orders orders = orderRepository.findById(request.getOrdersId())
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            existingSample.setOrders(orders);
        }

        samplesMapper.updateSamples(existingSample, request);
        existingSample.setUpdatedAt(new Date());

        var savedSample = samplesRepository.save(existingSample);
        var response = samplesMapper.toSamplesResponse(savedSample);
        response.setUserId(savedSample.getUser().getId());
        response.setSampleKitsId(savedSample.getSampleKits().getId());
        response.setOrdersId(savedSample.getOrders().getId());
        response.setTestResultId(savedSample.getTestResult().getId());

        log.info("Sample updated successfully: {}", response);
        return response;
    }


    public void deleteSample(String id) {
        log.info("Deleting sample with ID: {}", id);
        
        if (!samplesRepository.existsById(id)) {
            throw new AppException(ErrorCode.SAMPLE_NOT_FOUND);
        }
        
        samplesRepository.deleteById(id);
        log.info("Sample deleted successfully");
    }

    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}