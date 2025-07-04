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
public class SampleKitsRequest {

            String kit_code;
            String kit_type;
            String status;
            String shipper_data;
            Date delivered_date;
            int tracking_number ;
            String shipping_address;
            Date expiry_date;
            String instruction;

            String samplesId ;
            String orderId ;
}
