package swp_project.dna_service.dto.response;


import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.entity.Role;
import java.time.LocalDate;
import java.util.Set;

@Value
@Valid
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String username ;
    String password;
    String email ;
    String full_name;
    LocalDate dob;

    Set<Role> roles;

}