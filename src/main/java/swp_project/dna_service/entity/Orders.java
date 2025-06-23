package swp_project.dna_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

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
    String id;
    int order_code;
    String status ;
    float total_amount ;
    String payment_method ;
    String payment_status ;
    String payment_date ;
    String transaction_id ;
    String notes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at" )
    Date createdAt = new java.util.Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt = new java.util.Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = false)
    User user;

    @OneToOne(mappedBy = "orders")
    Review review;


    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;

}
