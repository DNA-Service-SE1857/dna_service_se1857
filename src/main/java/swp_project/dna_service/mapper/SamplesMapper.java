package swp_project.dna_service.mapper;


import org.mapstruct.*;
import swp_project.dna_service.dto.request.AppointmentRequest;
import swp_project.dna_service.dto.request.SamplesRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.dto.response.SamplesResponse;
import swp_project.dna_service.entity.Appointment;
import swp_project.dna_service.entity.Samples;

@Mapper(componentModel = "spring")
public interface SamplesMapper {

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Samples toSamples(SamplesRequest request);

    SamplesResponse toSamplesResponse(Samples samples);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSamples(@MappingTarget Samples samples, SamplesRequest request);

}
