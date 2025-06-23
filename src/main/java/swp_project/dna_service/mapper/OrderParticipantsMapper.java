package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.OrderParticipantsRequest;
import swp_project.dna_service.dto.response.OrderParticipantsResponse;
import swp_project.dna_service.entity.OrderParticipants;

@Mapper(componentModel = "spring")
public interface OrderParticipantsMapper {

    OrderParticipants toOrderParticipants(OrderParticipantsRequest orderParticipants);

    OrderParticipantsResponse toOrderParticipantsResponse(OrderParticipants order) ;

    void updateOrderParticipants(@MappingTarget OrderParticipants order, OrderParticipantsRequest orderParticipants);

}
