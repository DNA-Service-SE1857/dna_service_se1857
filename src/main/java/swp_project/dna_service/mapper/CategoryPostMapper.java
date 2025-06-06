package swp_project.dna_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import swp_project.dna_service.dto.request.CategoryPostRequest;
import swp_project.dna_service.dto.response.CategoryPostResponse;
import swp_project.dna_service.entity.CategoryPost;
import swp_project.dna_service.entity.PostStatus;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryPostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "posts", ignore = true)
    CategoryPost toCategoryPost(CategoryPostRequest request);

    @Mapping(target = "postsCount", source = "posts", qualifiedByName = "countPosts")
    CategoryPostResponse toCategoryPostResponse(CategoryPost categoryPost);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "posts", ignore = true)
    void updateCategoryPost(@MappingTarget CategoryPost categoryPost, CategoryPostRequest request);
    
    @Named("countPosts")
    default int countPosts(List<PostStatus> posts) {
        return posts != null ? posts.size() : 0;
    }
}
