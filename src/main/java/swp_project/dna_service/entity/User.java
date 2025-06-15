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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    Date updatedAt = new Date();


    @ManyToMany(cascade = CascadeType.PERSIST)
    Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<PostStatus> postStatuses;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE} , orphanRemoval = true)
    Set<Notifications> notifications;

}
