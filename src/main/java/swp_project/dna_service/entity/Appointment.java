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
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    Date appointment_date ;
    String appointment_type ;
    boolean status ;
    String notes ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at" )
    Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" ,nullable = false)
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_time_slot_id" ,nullable = false)
    DoctorTimeSlot doctorTimeSlot;

}
