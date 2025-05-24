package swp_project.dna_service.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Value
@Valid
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    @Size(min = 3, max = 10 , message = "Username must be between 3 and 10 characters")
    String username ;
    @Size(min = 3, max = 10 , message = "Password must be between 3 and 10 characters")
    String password;
    String firstname ;
    String lastname ;
    LocalDate dob;
}
