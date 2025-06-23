package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp_project.dna_service.entity.DoctorCertificate;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorCertificateRepository extends JpaRepository<DoctorCertificate, String> {

    List<DoctorCertificate> findAllByDoctor_Id(String doctorId);

    Optional<DoctorCertificate> findByLicensceNumber(String licensceNumber);

    List<DoctorCertificate> findByIsActive(Boolean isActive);

    Optional<DoctorCertificate> findByIdAndDoctor_Id(String id, String doctorId);
}
