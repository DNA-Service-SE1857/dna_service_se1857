package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.RoleRequest;
import swp_project.dna_service.dto.response.RoleResponse;
import swp_project.dna_service.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    void updateRole(@MappingTarget Role role, RoleRequest request);
}
