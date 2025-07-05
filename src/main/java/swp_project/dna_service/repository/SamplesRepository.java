package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.entity.Samples;
import swp_project.dna_service.entity.User;

import java.util.List;

public interface SamplesRepository extends JpaRepository<Samples, String> {

    List<Samples> findByUserId(String userId);

}
