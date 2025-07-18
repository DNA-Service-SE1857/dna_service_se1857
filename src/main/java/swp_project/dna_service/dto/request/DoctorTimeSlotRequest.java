package swp_project.dna_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorTimeSlotRequest {

    Integer dayOfWeek;
    LocalDate specificDate;
    LocalTime startTime;
    LocalTime endTime;
    Boolean isAvailable;
    String doctorId;
}
