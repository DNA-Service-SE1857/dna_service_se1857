package swp_project.dna_service.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.entity.SampleKits;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SamplesResponse {
    String id;
    String sample_code;
    String sample_type;

    Date collection_date;
    Date received_date;

    String status;
    String shipping_tracking;
    String notes;
    String sample_quality;

    String userId;
    String sampleKitsId;
}
