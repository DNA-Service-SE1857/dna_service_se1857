package swp_project.dna_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.ReviewRequest;
import swp_project.dna_service.dto.response.ReviewResponse;
import swp_project.dna_service.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequest request);

    ReviewResponse toReviewResponse(Review review);

    void updateReview(@MappingTarget Review review, ReviewRequest request);
}