package swp_project.dna_service.service;


import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.RoleRequest;
import swp_project.dna_service.dto.response.RoleResponse;
import swp_project.dna_service.mapper.RoleMapper;
import swp_project.dna_service.repository.RoleRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class RoleService {

    RoleRepository roleReponsitory;


    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        {
            var role = roleMapper.toRole(request);

            role = roleReponsitory.save(role);
            return roleMapper.toRoleResponse(role);
        }
    }

    public List<RoleResponse> getAllRole() {
        var roles = roleReponsitory.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String role) {
        roleReponsitory.deleteById(role);
    }
}