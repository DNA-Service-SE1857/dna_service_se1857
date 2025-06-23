package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, String> {

}
