package swp_project.dna_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.ServiceRequest;
import swp_project.dna_service.dto.response.ServiceResponse;
import swp_project.dna_service.entity.Dna_Service;


@Mapper(componentModel = "spring")
public interface ServiceMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Dna_Service toService(ServiceRequest request);

    ServiceResponse toServiceResponse(Dna_Service service);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateService(@MappingTarget Dna_Service service, ServiceRequest request);
}