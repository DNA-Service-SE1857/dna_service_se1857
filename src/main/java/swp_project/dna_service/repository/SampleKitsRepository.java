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

    @Query("SELECT sk FROM SampleKits sk LEFT JOIN FETCH sk.samples LEFT JOIN FETCH sk.user WHERE sk.samples.id = :samplesId")
    List<SampleKits> findBySamplesId(@Param("samplesId") String samplesId);

    List<SampleKits> findByOrders_Id(String orderId);
}
