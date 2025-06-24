package swp_project.dna_service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import swp_project.dna_service.dto.request.MedicalRecordRequest;
import swp_project.dna_service.dto.response.MedicalRecordResponse;
import swp_project.dna_service.entity.MedicalRecord;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

     MedicalRecordResponse toResponse(MedicalRecord entity);

     MedicalRecord toEntity(MedicalRecordRequest request);

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     void updateMedicalRecord(@MappingTarget MedicalRecord medicalRecord, MedicalRecordRequest request);
}
