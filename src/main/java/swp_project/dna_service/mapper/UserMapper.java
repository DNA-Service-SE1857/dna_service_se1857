package swp_project.dna_service.mapper;


import org.mapstruct.*;
import swp_project.dna_service.dto.request.UserCreationRequest;
import swp_project.dna_service.dto.request.UserUpdateRequest;
import swp_project.dna_service.dto.response.UserResponse;
import swp_project.dna_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser (UserCreationRequest request);

    @Mapping(target = "roles", source = "roles")
    UserResponse toUserResponse (User user);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser (@MappingTarget User user, UserUpdateRequest request);
}
