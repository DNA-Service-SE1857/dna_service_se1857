package swp_project.dna_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorTimeSlotResponse {

    String id;
    Integer dayOfWeek;
    LocalDate specificDate;
    LocalTime startTime;
    LocalTime endTime;
    Boolean isAvailable;
    Date createdAt;
    String doctorId;

}
