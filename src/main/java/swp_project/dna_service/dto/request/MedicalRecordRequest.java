package swp_project.dna_service.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class MedicalRecordRequest {

    int recordCode;
    String medicalHistory;
    String allergies;
    String medications;
    String healthConditions;
    String emergencyContactPhone;
    String emergencyContactName;
    Date createdAt;
    Date updatedAt;
    String userId;
}
