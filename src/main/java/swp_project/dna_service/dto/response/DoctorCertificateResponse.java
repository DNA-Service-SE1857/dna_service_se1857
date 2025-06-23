package swp_project.dna_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorCertificateResponse {
    String id;
    String certificateName;
    String licenseNumber;
    Date issueDate;
    Date expiryDate;
    Boolean isActive;
    Date createdAt;
    String issuedBy;
    String doctorId;
}
