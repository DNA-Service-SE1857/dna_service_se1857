package swp_project.dna_service.configuration;


import java.util.HashSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import swp_project.dna_service.entity.Role;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    Role Role;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userReponsitory) {
        return args -> {
            if (userReponsitory.findUserByUsername("admin").isEmpty()) {
               Role adminRole =
                        Role.builder()
                                .name("ROLE_ADMIN")
                                .description("Administrator role")
                                .build();

                HashSet<Role> roles = new HashSet<>();
                roles.add(adminRole);

                User user =
                        User.builder()
                                .username("admin")
                                .email("phat@hi2.in")
                                .full_name("Administrator")
                                .roles(roles)
                                .password(passwordEncoder.encode("admin"))
                                .createdAt(new java.util.Date())
                                .updatedAt(new java.util.Date())
                                .build();

                userReponsitory.save(user);
                log.warn("Create admin user with password: admin");
            }
        };
    }
}