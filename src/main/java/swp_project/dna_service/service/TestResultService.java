package swp_project.dna_service.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.TestResultRequest;
import swp_project.dna_service.dto.response.TestResultResponse;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.entity.Samples;
import swp_project.dna_service.entity.TestResult;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.TestResultMapper;
import swp_project.dna_service.repository.OrderRepository;
import swp_project.dna_service.repository.SamplesRepository;
import swp_project.dna_service.repository.TestResultRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestResultService {

    TestResultMapper testResultMapper;
    TestResultRepository testResultRepository;
    SamplesRepository samplesRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    public TestResultResponse createTestResult(TestResultRequest request) {
        String userId = extractUserIdFromJwt();
        log.info("Creating test result for user: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var testResult = testResultMapper.toTestResult(request);
        testResult.setUser(user);
        testResult.setSamples(new ArrayList<>());

        if (request.getOrders_id() != null && !request.getOrders_id().isEmpty() ) {
            Orders orders = orderRepository.findById(request.getOrders_id())
                    .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

            testResult.setOrders(orders);
            orders.setTestResult(testResult);
        }

        testResult = testResultRepository.save(testResult);


        if (request.getSample_id() != null && !request.getSample_id().isEmpty()) {
            List<Samples> samplesList = new ArrayList<>();

            for (String sampleId : request.getSample_id()) {
                Samples sample = samplesRepository.findById(sampleId)
                        .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_NOT_FOUND));
                sample.setTestResult(testResult);
                samplesList.add(sample);
            }

            testResult.setSamples(samplesList);
            samplesRepository.saveAll(samplesList);
        }


        TestResultResponse response = testResultMapper.toTestResultResponse(testResult);
        response.setUserId(userId);
        response.setId(testResult.getId());
        response.setSamplesId(testResult.getSamples().stream()
                .map(Samples::getId)
                .toList()
                .toString());
        response.setOrders_id(testResult.getOrders().getId());

        return response;
    }


    @Transactional
    public List<TestResultResponse> getAllTestResults() {
        String userId = extractUserIdFromJwt();
        log.info("Getting all test results for user: {}", userId);

        List<TestResult> testResults = testResultRepository.findByUserId(userId);

        return testResults.stream()
                .map(result -> {
                    TestResultResponse response = testResultMapper.toTestResultResponse(result);
                    response.setUserId(result.getUser().getId());
                    response.setOrders_id(result.getOrders().getId());
                    response.setSamplesId(result.getSamples().stream()
                            .map(Samples::getId)
                            .toList()
                            .toString());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public TestResultResponse getTestResultById(String id) {
        String userId = extractUserIdFromJwt();
        log.info("Getting test result with id: {} for user: {}", id, userId);

        TestResult testResult = testResultRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEST_RESULT_NOT_FOUND));

        TestResultResponse response = testResultMapper.toTestResultResponse(testResult);
        response.setUserId(testResult.getUser().getId());
        response.setOrders_id(testResult.getOrders().getId());
        response.setSamplesId(testResult.getSamples().stream()
                .map(Samples::getId)
                .toList()
                .toString());

        return response;
    }

    @Transactional
    public List<TestResultResponse> getTestResultsByOrderId(String orderId) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        List<TestResult> testResults = testResultRepository.findByOrdersId(orderId);

        return testResults.stream()
                .map(result -> {
                    TestResultResponse response = testResultMapper.toTestResultResponse(result);
                    response.setUserId(result.getUser().getId());
                    response.setOrders_id(result.getOrders().getId());
                    response.setSamplesId(result.getSamples().stream()
                            .map(Samples::getId)
                            .toList()
                            .toString());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TestResultResponse> getTestResultsBySampleId(String sampleId) {
        String userId = extractUserIdFromJwt();
        log.info("Getting test results for sample: {} and user: {}", sampleId, userId);

        Samples sample = samplesRepository.findById(sampleId)
                .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_NOT_FOUND));

        if (!sample.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        List<TestResult> testResults = testResultRepository.findBySamplesId(sampleId);

        return testResults.stream()
                .map(result -> {
                    TestResultResponse response = testResultMapper.toTestResultResponse(result);
                    response.setUserId(result.getUser().getId());
                    response.setOrders_id(result.getOrders().getId());
                    response.setSamplesId(result.getSamples().stream()
                            .map(Samples::getId)
                            .toList()
                            .toString());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public TestResultResponse updateTestResult(String id, TestResultRequest request) {
        String userId = extractUserIdFromJwt();
        log.info("Updating test result with id: {} for user: {}", id, userId);

        TestResult existingTestResult = testResultRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEST_RESULT_NOT_FOUND));

        if (!existingTestResult.getUser().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        if (request.getResult_type() != null) {
            existingTestResult.setResult_type(request.getResult_type());
        }
        if (request.getResult_percentage() != null) {
            existingTestResult.setResult_percentage(request.getResult_percentage());
        }
        if (request.getConclusion() != null) {
            existingTestResult.setConclusion(request.getConclusion());
        }
        if (request.getResult_detail() != null) {
            existingTestResult.setResult_detail(request.getResult_detail());
        }
        if (request.getResult_file() != null) {
            existingTestResult.setResult_file(request.getResult_file());
        }
        if (request.getTested_date() != null) {
            existingTestResult.setTested_date(request.getTested_date());
        }

        if (request.getSample_id() != null && !request.getSample_id().isEmpty()) {
            List<Samples> sampleList = request.getSample_id().stream()
                    .map(sampleId -> samplesRepository.findById(sampleId)
                            .orElseThrow(() -> new AppException(ErrorCode.SAMPLE_NOT_FOUND)))
                    .collect(Collectors.toList());

            for (Samples sample : sampleList) {
                sample.setTestResult(existingTestResult);
            }

            existingTestResult.setSamples(sampleList);
        }



        TestResult updated = testResultRepository.save(existingTestResult);


        TestResultResponse response = testResultMapper.toTestResultResponse(updated);
        response.setUserId(updated.getUser().getId());


        if (updated.getOrders() != null) {
            response.setOrders_id(updated.getOrders().getId());
        }

        if (updated.getSamples() != null) {
            List<String> sampleIds = updated.getSamples().stream()
                    .map(Samples::getId)
                    .collect(Collectors.toList());
            response.setSamplesId(sampleIds.toString());
        }

        return response;
    }


    public void deleteTestResult(String id) {
        String userId = extractUserIdFromJwt();
        log.info("Deleting test result with id: {} for user: {}", id, userId);

        TestResult testResult = testResultRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEST_RESULT_NOT_FOUND));

        testResultRepository.delete(testResult);
        log.info("Test result with id: {} deleted successfully for user: {}", id, userId);
    }

    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}
