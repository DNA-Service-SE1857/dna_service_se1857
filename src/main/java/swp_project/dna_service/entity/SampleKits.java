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
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@ToString
public class SampleKits {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    String kit_code;
    String kit_type;
    String status;
    String shipper_data = "Not Shipped";
    Date delivered_date = new Date();
    int tracking_number = 0;
    String shipping_address;
    Date expiry_date;
    String instruction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    Date updatedAt = new Date();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    Orders orders;


    @OneToMany(mappedBy = "sampleKits", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Samples> samples;

}
