package swp_project.dna_service.dto.response;

import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationsResponse {
    String id;
    String title;
    String message;
    String type;
    Boolean is_read;
    String userId;
}
