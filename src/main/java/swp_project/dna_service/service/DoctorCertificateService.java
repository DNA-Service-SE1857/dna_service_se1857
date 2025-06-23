package swp_project.dna_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.DoctorCertificateRequest;
import swp_project.dna_service.dto.response.DoctorCertificateResponse;
import swp_project.dna_service.entity.Doctor;
import swp_project.dna_service.entity.DoctorCertificate;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.DoctorCertificateMapper;
import swp_project.dna_service.repository.DoctorCertificateRepository;
import swp_project.dna_service.repository.DoctorRepository;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorCertificateService {

    DoctorCertificateRepository doctorCertificateRepository;
    DoctorRepository doctorRepository;
    DoctorCertificateMapper doctorCertificateMapper;

    public DoctorCertificateResponse createCertificate(DoctorCertificateRequest request) {
        log.info("Creating certificate for doctorId: {}", request.getDoctorId());

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", request.getDoctorId());
                    return new AppException(ErrorCode.DOCTOR_NOT_FOUND);
                });

        try {
            DoctorCertificate certificate = doctorCertificateMapper.toDoctorCertificate(request, doctor);
            certificate.setCreatedAt(new Date());

            certificate = doctorCertificateRepository.save(certificate);
            log.info("Certificate created successfully with ID: {}", certificate.getId());

            return doctorCertificateMapper.toDoctorCertificateResponse(certificate);
        } catch (Exception e) {
            log.error("Error creating certificate: {}", e.getMessage());
            throw new AppException(ErrorCode.CERTIFICATE_CREATION_FAILED);
        }
    }

    public DoctorCertificateResponse updateCertificate(String certificateId, DoctorCertificateRequest request) {
        log.info("Updating certificate with ID: {}", certificateId);

        DoctorCertificate existing = doctorCertificateRepository.findById(certificateId)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        try {
            // Kiểm tra nếu doctorId thay đổi
            if (!existing.getDoctor().getId().equals(request.getDoctorId())) {
                Doctor newDoctor = doctorRepository.findById(request.getDoctorId())
                        .orElseThrow(() -> {
                            log.error("Doctor not found with ID: {}", request.getDoctorId());
                            return new AppException(ErrorCode.DOCTOR_NOT_FOUND);
                        });
                existing.setDoctor(newDoctor);
            }

            doctorCertificateMapper.updateDoctorCertificate(existing, request);
            existing.setUpdatedAt(new Date());

            existing = doctorCertificateRepository.save(existing);
            log.info("Certificate updated successfully with ID: {}", existing.getId());

            return doctorCertificateMapper.toDoctorCertificateResponse(existing);
        } catch (Exception e) {
            log.error("Error updating certificate: {}", e.getMessage());
            throw new AppException(ErrorCode.CERTIFICATE_UPDATE_FAILED);
        }
    }

    public DoctorCertificateResponse getCertificateById(String certificateId) {
        log.info("Fetching certificate with ID: {}", certificateId);

        DoctorCertificate certificate = doctorCertificateRepository.findById(certificateId)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        return doctorCertificateMapper.toDoctorCertificateResponse(certificate);
    }

    public List<DoctorCertificateResponse> getCertificatesByDoctorId(String doctorId) {
        log.info("Fetching certificates for doctor ID: {}", doctorId);

        List<DoctorCertificate> certificates = doctorCertificateRepository.findAllByDoctor_Id(doctorId);
        log.info("Found {} certificates for doctor ID: {}", certificates.size(), doctorId);

        return certificates.stream()
                .map(doctorCertificateMapper::toDoctorCertificateResponse)
                .toList();
    }

    public List<DoctorCertificateResponse> getAllCertificates() {
        log.info("Fetching all doctor certificates");

        List<DoctorCertificate> certificates = doctorCertificateRepository.findAll();
        return certificates.stream()
                .map(doctorCertificateMapper::toDoctorCertificateResponse)
                .toList();
    }

    public void deleteCertificate(String certificateId) {
        log.info("Deleting certificate with ID: {}", certificateId);

        DoctorCertificate certificate = doctorCertificateRepository.findById(certificateId)
                .orElseThrow(() -> new AppException(ErrorCode.CERTIFICATE_NOT_FOUND));

        doctorCertificateRepository.delete(certificate);
        log.info("Certificate with ID: {} deleted successfully", certificateId);
    }
}
