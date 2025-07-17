package swp_project.dna_service.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class OrderDetailResponse {

    String id;
    int quantity;
    float unit_price;
    float subtotal;
    String note;
    String dnaServiceId;
    String orderId;
    Date createdAt;
    Date updatedAt;
}
