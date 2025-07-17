package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.OrderDetail;
import swp_project.dna_service.entity.OrderParticipants;

import java.util.List;

public interface OrderParticipantsRepository  extends JpaRepository<OrderParticipants, String> {

    List<OrderParticipants> findByOrders_Id(String orderId);
}
