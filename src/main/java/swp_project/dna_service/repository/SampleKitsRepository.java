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

    @Query("SELECT sk FROM SampleKits sk LEFT JOIN FETCH sk.samples WHERE sk.orders.id = :orderId")
    List<SampleKits> findByOrdersIdWithSamples(@Param("orderId") String orderId);

    @Query("SELECT sk FROM SampleKits sk WHERE sk.orderParticipants.id = :orderParticipantsId")
    Optional<SampleKits> findByOrderParticipantsId(@Param("orderParticipantsId") String orderParticipantsId);

    @Query("SELECT sk FROM SampleKits sk LEFT JOIN FETCH sk.samples WHERE sk.user.id = :userId")
    List<SampleKits> findByUserIdWithSamples(@Param("userId") String userId);
}