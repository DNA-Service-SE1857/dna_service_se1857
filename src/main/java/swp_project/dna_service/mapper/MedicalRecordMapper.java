package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.MedicalRecordRequest;
import swp_project.dna_service.dto.response.MedicalRecordResponse;
import swp_project.dna_service.entity.MedicalRecord;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

     MedicalRecordResponse toResponse(MedicalRecord entity);

     MedicalRecord toEntity(MedicalRecordRequest request);

     void updateMedicalRecord(@MappingTarget MedicalRecord medicalRecord, MedicalRecordRequest request);
}
