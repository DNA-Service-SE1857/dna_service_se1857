package swp_project.dna_service.mapper;


import org.mapstruct.*;
import swp_project.dna_service.dto.request.AppointmentRequest;
import swp_project.dna_service.dto.request.UserCreationRequest;
import swp_project.dna_service.dto.request.UserUpdateRequest;
import swp_project.dna_service.dto.response.AppointmentResponse;
import swp_project.dna_service.dto.response.UserResponse;
import swp_project.dna_service.entity.Appointment;
import swp_project.dna_service.entity.User;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    Appointment toAppointment(AppointmentRequest request);

    @Mapping(target = "id", ignore = true)
    AppointmentResponse toUserResponse(Appointment appointment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAppointment(@MappingTarget Appointment appointment, AppointmentRequest request);
}
