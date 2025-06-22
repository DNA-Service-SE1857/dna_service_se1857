package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrders_Id(String orderId);
}
