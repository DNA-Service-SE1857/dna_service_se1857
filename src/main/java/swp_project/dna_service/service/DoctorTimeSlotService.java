package swp_project.dna_service.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.DoctorTimeSlotRequest;
import swp_project.dna_service.dto.response.DoctorTimeSlotResponse;
import swp_project.dna_service.entity.Doctor;
import swp_project.dna_service.entity.DoctorTimeSlot;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.DoctorTimeSlotMapper;
import swp_project.dna_service.repository.DoctorRepository;
import swp_project.dna_service.repository.DoctorTimeSlotRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorTimeSlotService {

    DoctorTimeSlotRepository slotRepository;
    DoctorRepository doctorRepository;
    DoctorTimeSlotMapper mapper;

    public DoctorTimeSlotResponse create(DoctorTimeSlotRequest request) {
        log.info("Creating doctor time slot: {}", request);

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", request.getDoctorId());
                    return new AppException(ErrorCode.DOCTOR_NOT_FOUND);
                });

        DoctorTimeSlot slot = mapper.toDoctorTimeSlot(request);
        slot.setDoctor(doctor);
        slot.setCreatedAt(new Date());

        DoctorTimeSlot savedSlot = slotRepository.save(slot);
        log.info("Created doctor time slot with ID: {}", savedSlot.getId());

        return mapper.toDoctorTimeSlotResponse(savedSlot);
    }

    public DoctorTimeSlotResponse update(String slotId, DoctorTimeSlotRequest request) {
        log.info("Updating doctor time slot with ID: {}", slotId);

        DoctorTimeSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> {
                    log.error("Doctor time slot not found: {}", slotId);
                    return new AppException(ErrorCode.DOCTOR_SLOT_NOT_FOUND);
                });

        mapper.updateDoctorTimeSlot(slot, request);

        if (request.getDoctorId() != null && !request.getDoctorId().equals(slot.getDoctor().getId())) {
            Doctor newDoctor = doctorRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_NOT_FOUND));
            slot.setDoctor(newDoctor);
        }

        DoctorTimeSlot updatedSlot = slotRepository.save(slot);
        log.info("Updated doctor time slot with ID: {}", updatedSlot.getId());

        return mapper.toDoctorTimeSlotResponse(updatedSlot);
    }

    public DoctorTimeSlotResponse getById(String slotId) {
        DoctorTimeSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_SLOT_NOT_FOUND));
        return mapper.toDoctorTimeSlotResponse(slot);
    }

    public List<DoctorTimeSlotResponse> getAll() {
        return slotRepository.findAll()
                .stream()
                .map(mapper::toDoctorTimeSlotResponse)
                .collect(Collectors.toList());
    }

    public void delete(String slotId) {
        log.info("Deleting doctor time slot with ID: {}", slotId);

        DoctorTimeSlot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new AppException(ErrorCode.DOCTOR_SLOT_NOT_FOUND));

        slotRepository.delete(slot);
        log.info("Deleted doctor time slot with ID: {}", slotId);
    }
}
