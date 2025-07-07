package swp_project.dna_service.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SampleKitsResponse {

    String id ;

    String kit_code;
    String kit_type;
    String status;
    String shipper_data;
    Date delivered_date;
    int tracking_number ;
    String shipping_address;
    Date expiry_date;
    String instruction;

    Date createdAt;
    Date updatedAt;

    String order_participants_id ;
    String samplesId ;
    String userId;
    String orderId ;
}
