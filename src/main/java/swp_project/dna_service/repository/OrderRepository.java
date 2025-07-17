package swp_project.dna_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Orders;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, String> {

    List<Orders> findByUserId(String userId);


}
