package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp_project.dna_service.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
}
