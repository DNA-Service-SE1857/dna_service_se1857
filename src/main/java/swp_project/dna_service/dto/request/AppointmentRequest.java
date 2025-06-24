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
public class AppointmentRequest {
    Date appointment_date ;
    String appointment_type ;
    boolean status ;
    String notes ;
    String doctor_time_slot;
}