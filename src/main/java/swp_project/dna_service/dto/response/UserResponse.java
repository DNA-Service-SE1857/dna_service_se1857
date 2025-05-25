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
public class UserCreationResponse {

    String id;
    String username ;
    String password;
    String email ;
    String full_name;
    LocalDate dob;

}
