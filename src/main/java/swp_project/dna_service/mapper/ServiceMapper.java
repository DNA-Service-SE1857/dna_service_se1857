package swp_project.dna_service.mapper;

import org.mapstruct.*;
import swp_project.dna_service.dto.request.ServiceRequest;
import swp_project.dna_service.dto.response.ServiceResponse;
import swp_project.dna_service.entity.Dna_Service;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Dna_Service toService(ServiceRequest request);

    @Mapping(source = "id", target = "serviceId")
    @Mapping(source = "user.id", target = "userId")
    ServiceResponse toServiceResponse(Dna_Service service);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)

    void updateService(@MappingTarget Dna_Service service, ServiceRequest request);
}