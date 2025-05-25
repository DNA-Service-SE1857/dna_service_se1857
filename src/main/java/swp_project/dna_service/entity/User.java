package swp_project.dna_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.FieldDefaults;
import swp_project.dna_service.validator.DobContraint;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id ;
    String username;
    String password ;
    @Email(message = "EMAIL_INVALID")
    String email ;
    String full_name;

    @DobContraint(min = 10, message = "DOB_INVALID")
    LocalDate dob;

    @ManyToMany(cascade = CascadeType.PERSIST)
    Set<Role> roles;
}
