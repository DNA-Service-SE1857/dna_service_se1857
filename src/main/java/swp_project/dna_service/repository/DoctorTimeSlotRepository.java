package swp_project.dna_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp_project.dna_service.entity.DoctorTimeSlot;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorTimeSlotRepository extends JpaRepository<DoctorTimeSlot, String> {

    List<DoctorTimeSlot> findAllByDoctor_Id(String doctorId);

    Optional<DoctorTimeSlot> findByIdAndDoctor_Id(String id, String doctorId);

    List<DoctorTimeSlot> findAllByDayOfWeekAndDoctor_Id(Integer dayOfWeek, String doctorId);

    List<DoctorTimeSlot> findAllByIsAvailable(Boolean isAvailable);
}
