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
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String taskTitle;
    String taskDescription;
    String taskType;
    String status;

    @Temporal(TemporalType.TIMESTAMP)
    Date dueDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date completedDate;

    String notes;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    Date createdAt = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt = new Date();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    Dna_Service dnaService;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id", nullable = false)
    OrderDetail orderDetail;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_record_id", nullable = false)
    MedicalRecord medicalRecord;
}
