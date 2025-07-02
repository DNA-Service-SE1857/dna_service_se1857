package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.MedicalRecordRequest;
import swp_project.dna_service.dto.response.MedicalRecordResponse;
import swp_project.dna_service.entity.MedicalRecord;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.MedicalRecordMapper;
import swp_project.dna_service.repository.MedicalRecordRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicalRecordService {

    MedicalRecordRepository medicalRecordRepository;
    UserRepository userRepository;
    MedicalRecordMapper medicalRecordMapper;

    public MedicalRecordResponse createRecord(MedicalRecordRequest request) {
        log.info("Creating medical record: {}", request);
        String userId = extractUserIdFromJwt();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        MedicalRecord record = medicalRecordMapper.toEntity(request);
        record.setUser(user);
        record.setCreatedAt(new Date());
        record.setUpdatedAt(new Date());

        MedicalRecord saved = medicalRecordRepository.save(record);
        MedicalRecordResponse response = medicalRecordMapper.toResponse(saved);
        response.setUserId(userId);
        return response;
    }

    public MedicalRecordResponse createRecordByUserId(MedicalRecordRequest request , String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        MedicalRecord record = medicalRecordMapper.toEntity(request);
        record.setUser(user);
        record.setCreatedAt(new Date());
        record.setUpdatedAt(new Date());

        MedicalRecord saved = medicalRecordRepository.save(record);
        MedicalRecordResponse response = medicalRecordMapper.toResponse(saved);
        response.setUserId(userId);
        return response;
    }

    public MedicalRecordResponse getById(String id) {
        log.info("Fetching medical record by ID: {}", id);
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOT_FOUND));
        return medicalRecordMapper.toResponse(record);
    }

    public List<MedicalRecordResponse> getAll() {
        log.info("Fetching all medical records");
        return medicalRecordRepository.findAll().stream()
                .map(medicalRecordMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<MedicalRecordResponse> getByUserId() {
        String userId = extractUserIdFromJwt();
        log.info("Fetching medical records for user: {}", userId);

        List<MedicalRecord> records = medicalRecordRepository.findByUserId(userId);
        return records.stream()
                .map(record -> {
                    MedicalRecordResponse res = medicalRecordMapper.toResponse(record);
                    res.setUserId(userId);
                    return res;
                })
                .collect(Collectors.toList());
    }

    public List<MedicalRecordResponse> getByUserIdByPatch(String id) {

        List<MedicalRecord> records = medicalRecordRepository.findByUserId(id);
        return records.stream()
                .map(record -> {
                    MedicalRecordResponse res = medicalRecordMapper.toResponse(record);
                    res.setUserId(id);
                    return res;
                })
                .collect(Collectors.toList());
    }

    public MedicalRecordResponse updateRecord(String id, MedicalRecordRequest request) {
        log.info("Updating medical record ID: {}", id);
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RECORD_NOT_FOUND));

        medicalRecordMapper.updateMedicalRecord(record, request);
        record.setUpdatedAt(new Date());

        MedicalRecord updated = medicalRecordRepository.save(record);
        MedicalRecordResponse response = medicalRecordMapper.toResponse(updated);
        response.setUserId(record.getUser().getId());
        return response;
    }
    public void deleteById(String id) {
        log.info("Deleting medical record ID: {}", id);
        if (!medicalRecordRepository.existsById(id)) {
            throw new AppException(ErrorCode.RECORD_NOT_FOUND);
        }
        medicalRecordRepository.deleteById(id);
    }
    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

}
