package swp_project.dna_service.mapper;


import org.mapstruct.*;
import swp_project.dna_service.dto.request.PostRequest;
import swp_project.dna_service.dto.response.PostResponse;
import swp_project.dna_service.entity.PostStatus;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PostStatus toPostStatus(PostRequest request);

    @Mapping(target = "id", source = "id")
    PostResponse toPostResponse (PostStatus postStatus);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePost (@MappingTarget PostStatus PostStatus , PostRequest request);
}