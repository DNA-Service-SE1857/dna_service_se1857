package swp_project.dna_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorCertificateRequest {
    @NotBlank(message = "Certificate name is required")
    String certificateName;

    @NotBlank(message = "License number is required")
    String licenseNumber;

    @NotBlank(message = "Issued by is required")
    String issuedBy;

    @NotNull(message = "Issue date is required")
    Date issueDate;

    Date expiryDate;

    @NotBlank(message = "Doctor ID is required")
    String doctorId;
}
