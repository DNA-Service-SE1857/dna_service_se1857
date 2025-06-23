package swp_project.dna_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.DoctorCertificateRequest;
import swp_project.dna_service.dto.response.DoctorCertificateResponse;
import swp_project.dna_service.entity.Doctor;
import swp_project.dna_service.entity.DoctorCertificate;

@Mapper(componentModel = "spring")
public interface DoctorCertificateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "issueDate", source = "request.issueDate")
    @Mapping(target = "expiryDate", source = "request.expiryDate")
    @Mapping(target = "licenseNumber", source = "request.licenseNumber")
    DoctorCertificate toDoctorCertificate(DoctorCertificateRequest request, Doctor doctor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "issueDate", source = "issueDate")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "licenseNumber", source = "licenseNumber")
    void updateDoctorCertificate(@MappingTarget DoctorCertificate entity, DoctorCertificateRequest request);

    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "licenseNumber", source = "licenseNumber")
    DoctorCertificateResponse toDoctorCertificateResponse(DoctorCertificate entity);
}
