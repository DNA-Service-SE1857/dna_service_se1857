package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsUserByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
