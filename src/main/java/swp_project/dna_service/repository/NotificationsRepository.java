package swp_project.dna_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp_project.dna_service.entity.InvalidatedToken;
import swp_project.dna_service.entity.Notifications;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, String> {
    Notifications findByUserId(String userId);

    List<Notifications> findAllByUser_Id(String userId);

    Optional<Notifications> findByIdAndUser_Id(String id, String userId);



}