package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.DoctorRequest;
import swp_project.dna_service.dto.response.DoctorResponse;
import swp_project.dna_service.entity.Doctor;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toDoctor(DoctorRequest request);

    void updateDoctor (@MappingTarget Doctor doctor, DoctorRequest request);

    DoctorResponse toDoctorResponse(Doctor doctor);
}
