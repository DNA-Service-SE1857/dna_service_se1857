package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp_project.dna_service.entity.Doctor;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
    List<Doctor> findByIsActive(Boolean isActive);

    Optional<Doctor> findByDoctorCode(String doctorCode);

    List<Doctor> findAllByUser_Id(String userId);

    Optional<Doctor> findByIdAndUser_Id(String id, String userId);
}
