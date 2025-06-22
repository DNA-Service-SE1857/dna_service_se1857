package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.OrderDetailRequest;
import swp_project.dna_service.dto.request.RoleRequest;
import swp_project.dna_service.dto.response.OrderDetailResponse;
import swp_project.dna_service.dto.response.RoleResponse;
import swp_project.dna_service.entity.OrderDetail;
import swp_project.dna_service.entity.Role;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(source = "dnaService.id", target = "dnaServiceId")
    @Mapping(source = "orders.id", target = "orderId")
    OrderDetailResponse toResponse(OrderDetail entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dnaService", ignore = true)
    @Mapping(target = "orders", ignore = true)
    OrderDetail toOrderDetail(OrderDetailRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dnaService", ignore = true)
    @Mapping(target = "orders", ignore = true)
    void updateOrderDetail(@MappingTarget OrderDetail orderDetail, OrderDetailRequest request);
}
