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
public class DoctorCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String certificateName;

    String licenseNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "issue_date")
    Date issueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "expiry_date")
    Date expiryDate;

    Boolean isActive;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    Date updatedAt;

    String issuedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    Doctor doctor;
}
