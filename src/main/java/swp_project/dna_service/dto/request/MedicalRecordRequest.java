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

    int record_code ;
    String medical_history ;
    String allergies ;
    String medications ;
    String health_conditions ;
    String emergency_contact_phone ;
    String emergency_contact_name ;
}
