package swp_project.dna_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class OrderParticipantsResponse {
    String id;
    String participant_name;
    String relationship;
    int age ;
    String note ;
    String order_id;
}
