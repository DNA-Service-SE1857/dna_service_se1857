package swp_project.dna_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.entity.Dna_Service;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Builder
public class AppointmentResponse {

    String id ;
    Date appointment_date ;
    String appointment_type ;
    boolean status ;
    String notes ;
    String userId;

    Set<Dna_Service> dna_services ;

    Date createdAt;
    Date updatedAt;
}
