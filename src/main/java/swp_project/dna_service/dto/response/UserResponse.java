package swp_project.dna_service.dto.response;


import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Value
@Valid
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String username ;
    String password;
    String firstname;
    String lastname;
    LocalDate dob;
}
