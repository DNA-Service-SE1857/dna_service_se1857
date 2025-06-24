package swp_project.dna_service.mapper;


import org.mapstruct.*;
import swp_project.dna_service.dto.request.SampleKitsRequest;
import swp_project.dna_service.dto.response.SampleKitsResponse;
import swp_project.dna_service.entity.SampleKits;


@Mapper(componentModel = "spring")
public interface SampleKitsMapper {

    @Mapping(target = "id", ignore = true)
    SampleKits toSampleKit(SampleKitsRequest request);

    SampleKitsResponse toSampleKitResponse(SampleKits samples);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSampleKit(@MappingTarget SampleKits samples, SampleKitsRequest request);
}
