package swp_project.dna_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String title;
    String message;
    String type;
    Boolean is_read;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at" )
    Date createdAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = false)
    User user;
}

