package swp_project.dna_service.dto.response;

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
public class DoctorResponse {
    String userId;
    String doctorId ;
    String doctorCode;
    String doctorName;
    String doctorEmail;
    String doctorPhone;
    Boolean isActive;
    Date createdAt;
    Date updatedAt;
}
