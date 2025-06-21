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
public class OrderResponse {

    String userId;
    String orderId;
    int order_code;
    String status ;
    float total_amount ;
    String payment_method ;
    String payment_status ;
    String payment_date ;
    String transaction_id ;
    String notes;
    Date createdAt;
    Date updatedAt;

}
