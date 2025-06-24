package swp_project.dna_service.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@ToString
public class Samples {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;

    String sample_code ;
    String sample_type ;
    String collection_method ;

    @Temporal(TemporalType.TIMESTAMP)
    Date collection_date ;
    @Temporal(TemporalType.TIMESTAMP)
    Date received_date;

    String status ;
    String shipping_tracking ;
    String notes;
    String sample_quality ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    Date updatedAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToOne
    @JoinColumn(name = "orders_id", nullable = false)
    Orders orders;
}
