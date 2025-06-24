package swp_project.dna_service.mapper;


import org.mapstruct.*;
import swp_project.dna_service.dto.request.OrderRequest;
import swp_project.dna_service.dto.response.OrderResponse;
import swp_project.dna_service.entity.Orders;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Orders toOrder(OrderRequest orderRequest);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "user.id", target = "userId")
    OrderResponse toOrderResponse(Orders orders);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrder(@MappingTarget Orders orders, OrderRequest orderRequest);
}