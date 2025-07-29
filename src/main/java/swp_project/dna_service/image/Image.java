package swp_project.dna_service.image;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.entity.DoctorCertificate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;

    String name ;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    byte[] imageData ;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorcertificate_id", nullable = false)
    DoctorCertificate doctorCertificate;

}
