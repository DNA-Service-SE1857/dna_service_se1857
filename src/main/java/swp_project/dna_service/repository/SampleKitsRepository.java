package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swp_project.dna_service.entity.SampleKits;
import swp_project.dna_service.entity.Samples;

import java.util.List;
import java.util.Optional;

public interface SampleKitsRepository extends JpaRepository<SampleKits, String> {

    List<SampleKits> findByUserId(String userId);

    List<SampleKits> findBySamplesId (String sampleId);

    List<SampleKits> findByOrders_Id(String orderId);
}
