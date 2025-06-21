package swp_project.dna_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, String> {
}
