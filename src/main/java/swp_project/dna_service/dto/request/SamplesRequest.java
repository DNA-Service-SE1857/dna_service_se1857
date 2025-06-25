package swp_project.dna_service.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SamplesRequest {
    String sample_code;
    String sample_type;
    String collection_method;

    Date collection_date;
    Date received_date;

    String status;
    String shipping_tracking;
    String notes;
    String sample_quality;

    String userId;
    String orderId;
    String sampleKitsId;
}
