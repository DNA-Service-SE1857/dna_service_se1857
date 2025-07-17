package swp_project.dna_service.entity;


import jakarta.persistence.*;
import jdk.jfr.Timestamp;
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
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    String id ;
    int record_code ;
    String medical_history ;
    String allergies ;
    String medications ;
    String health_conditions ;
    String emergency_contact_phone ;
    String emergency_contact_name ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = false)
    User user;

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.ALL)
    List<Tasks> tasks;

}
