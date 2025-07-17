package swp_project.dna_service.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    Integer rating;
    String comment;
    String userId;
    String ordersId;
    String createdAt;
    String updatedAt;

}
