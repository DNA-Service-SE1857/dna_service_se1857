package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Dna_Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Dna_Service , String> {

    List<Dna_Service> findByUserId(String userId);
}
