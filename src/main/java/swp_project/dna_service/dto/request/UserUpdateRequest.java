package swp_project.dna_service.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.validator.DobContraint;

import java.time.LocalDate;

@Value
@Valid
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 3, max = 16 , message = "USERNAME_ERROR_CODE")
    String username ;
    @Size(min = 3, max = 16 , message = "PASS_ERROR_CODE")
    String password;
    String email ;
    String full_name;
    @DobContraint(min = 10, message = "DOB_INVALID")
    LocalDate dob;
}
