package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Dna_Service;

public interface ServiceRepository extends JpaRepository<Dna_Service , String> {

    Dna_Service findByUserId(String userId);
}
