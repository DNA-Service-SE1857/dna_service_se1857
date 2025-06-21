package swp_project.dna_service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import swp_project.dna_service.dto.request.OrderRequest;
import swp_project.dna_service.dto.response.OrderResponse;
import swp_project.dna_service.entity.Orders;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "user", ignore = true)
    Orders toOrder(OrderRequest orderRequest);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    OrderResponse toOrderResponse(Orders orders);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateOrder(@MappingTarget Orders orders, OrderRequest orderRequest);
}