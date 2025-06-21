package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.OrderRequest;
import swp_project.dna_service.dto.response.OrderResponse;
import swp_project.dna_service.entity.Orders;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Orders toOrder(OrderRequest orderRequest);
    OrderResponse toOrderResponse(Orders orders);
    void updateOrder(@MappingTarget Orders orders, OrderRequest orderRequest);
}