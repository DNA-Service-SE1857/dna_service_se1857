package swp_project.dna_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.service.DnaService;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;

    int quantity;
    float unit_price;
    float subtotal ;
    String note ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    Dna_Service dnaService;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Orders orders;

    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    List<Tasks> tasks;

}
