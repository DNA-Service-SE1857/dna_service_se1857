package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Role;
import swp_project.dna_service.entity.User;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role , String> {
}
