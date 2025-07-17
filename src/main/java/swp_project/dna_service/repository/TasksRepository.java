package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Tasks;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, String> {
    List<Tasks> findByOrderDetail_Id(String orderDetailId);
    List<Tasks> findByDnaService_Id(String dnaServiceId);
}
