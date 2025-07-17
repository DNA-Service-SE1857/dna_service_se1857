package swp_project.dna_service.mapper;

import org.mapstruct.*;
import swp_project.dna_service.dto.request.DoctorTimeSlotRequest;
import swp_project.dna_service.dto.response.DoctorTimeSlotResponse;
import swp_project.dna_service.entity.DoctorTimeSlot;

@Mapper(componentModel = "spring")
public interface DoctorTimeSlotMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    @Mapping(target = "doctor", ignore = true) // set trong service
    DoctorTimeSlot toDoctorTimeSlot(DoctorTimeSlotRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDoctorTimeSlot(@MappingTarget DoctorTimeSlot entity, DoctorTimeSlotRequest request);

    @Mapping(source = "doctor.id", target = "doctorId")
    DoctorTimeSlotResponse toDoctorTimeSlotResponse(DoctorTimeSlot doctorTimeSlot);
}
