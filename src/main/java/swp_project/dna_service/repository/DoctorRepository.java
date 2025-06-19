package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
