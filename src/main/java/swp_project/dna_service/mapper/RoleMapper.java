package swp_project.dna_service.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import swp_project.dna_service.dto.request.RoleRequest;
import swp_project.dna_service.dto.response.RoleResponse;
import swp_project.dna_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRole(@MappingTarget Role role, RoleRequest request);
}
