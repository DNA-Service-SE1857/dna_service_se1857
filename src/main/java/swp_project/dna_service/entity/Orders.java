package swp_project.dna_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String orderId;
    int order_code;
    String status ;
    float total_amount ;
    String payment_method ;
    String payment_status ;
    String payment_date ;
    String transaction_id ;
    String notes;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = false)
    User user;
}
