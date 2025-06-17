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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Dna_Service {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String service_name;
    String service_description;
    String service_category;
    String service_type;

    String imageUrl;
    Float test_price;

    int duration_days;
    int collection_method;

    boolean required_legal_document;
    boolean is_active;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at" )
    Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = false)
    User user;
}
