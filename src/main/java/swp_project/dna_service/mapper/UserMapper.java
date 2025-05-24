package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import swp_project.dna_service.dto.request.UserRequest;
import swp_project.dna_service.dto.response.UserResponse;
import swp_project.dna_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser (UserRequest request);

    UserResponse toUserResponse (User user);
}
