package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Review;
import swp_project.dna_service.entity.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByOrders_Id(String id);

    List<Review> findByUserId(String userId);
}
