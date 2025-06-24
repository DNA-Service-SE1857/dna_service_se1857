package swp_project.dna_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.DoctorTimeSlotRequest;
import swp_project.dna_service.dto.response.DoctorTimeSlotResponse;
import swp_project.dna_service.service.DoctorTimeSlotService;

import java.util.List;

@RestController
@RequestMapping("/doctor-time-slots")
@RequiredArgsConstructor
public class DoctorTimeSlotController {

    private final DoctorTimeSlotService doctorTimeSlotService;

    @PostMapping
    public ApiResponse<DoctorTimeSlotResponse> createDoctorTimeSlot(@RequestBody @Valid DoctorTimeSlotRequest request) {
        DoctorTimeSlotResponse response = doctorTimeSlotService.create(request);
        return ApiResponse.<DoctorTimeSlotResponse>builder()
                .code(200)
                .message("Doctor time slot created successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{slotId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse<DoctorTimeSlotResponse> updateDoctorTimeSlot(
            @PathVariable String slotId,
            @RequestBody @Valid DoctorTimeSlotRequest request) {
        DoctorTimeSlotResponse response = doctorTimeSlotService.update(slotId, request);
        return ApiResponse.<DoctorTimeSlotResponse>builder()
                .code(200)
                .message("Doctor time slot updated successfully")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<DoctorTimeSlotResponse>> getAllDoctorTimeSlots() {
        List<DoctorTimeSlotResponse> slots = doctorTimeSlotService.getAll();
        return ApiResponse.<List<DoctorTimeSlotResponse>>builder()
                .code(200)
                .message("Get all doctor time slots successfully")
                .result(slots)
                .build();
    }
    @GetMapping("/doctor/{doctorId}")
    public ApiResponse<List<DoctorTimeSlotResponse>> getDoctorTimeSlotsByDoctorId(@PathVariable String doctorId) {
        List<DoctorTimeSlotResponse> slots = doctorTimeSlotService.getByDoctorId(doctorId);
        return ApiResponse.<List<DoctorTimeSlotResponse>>builder()
                .code(200)
                .message("Get doctor time slots by doctor ID successfully")
                .result(slots)
                .build();
    }

    @GetMapping("/{slotId}")
    public ApiResponse<DoctorTimeSlotResponse> getDoctorTimeSlot(@PathVariable String slotId) {
        DoctorTimeSlotResponse response = doctorTimeSlotService.getById(slotId);
        return ApiResponse.<DoctorTimeSlotResponse>builder()
                .code(200)
                .message("Doctor time slot retrieved successfully")
                .result(response)
                .build();
    }

    @DeleteMapping("/{slotId}")
    public ApiResponse<Object> deleteDoctorTimeSlot(@PathVariable String slotId) {
        doctorTimeSlotService.delete(slotId);
        return ApiResponse.builder()
                .code(200)
                .message("Doctor time slot deleted successfully")
                .build();
    }
}
