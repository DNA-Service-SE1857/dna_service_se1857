package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.OrderRequest;
import swp_project.dna_service.dto.response.OrderResponse;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.OrderMapper;
import swp_project.dna_service.repository.UserRepository;
import swp_project.dna_service.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class    OrderService {

    UserRepository userRepository;
    OrderMapper orderMapper;
    OrderRepository orderRepository;

    // CREATE
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order with request: {}", request);   
        String userId = extractUserIdFromJwt();
        log.debug("Extracted user ID from JWT: {}", userId);   
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            
        Orders order = orderMapper.toOrder(request);
        order.setUser(user);
        order.setCreatedAt(new java.util.Date());
        order.setUpdatedAt(new java.util.Date());
        
        Orders savedOrder = orderRepository.save(order);

        OrderResponse response = orderMapper.toOrderResponse(savedOrder);
        response.setUserId(userId);
        response.setOrderId(savedOrder.getId());

        log.info("Returning order response: {}", response);
        return response ;
    }

    // READ
    public OrderResponse getOrderById(String orderId) {
        log.info("Getting order with ID: {}", orderId);
        Orders order = orderRepository.findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        log.info("Getting all orders");
        return orderRepository.findAll().stream()
            .map(orderMapper::toOrderResponse)
            .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByUserId(String userId) {
        log.info("Getting orders for user ID: {}", userId);
        
        userId = extractUserIdFromJwt();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        
        List<Orders> orders = orderRepository.findByUserId(userId);
        log.debug("Found {} orders for user ID: {}", orders.size(), userId);

        return orders.stream()
                .map(order -> {
                    OrderResponse response = orderMapper.toOrderResponse(order);
                    response.setOrderId(order.getId());
                    return response;
                })
                .collect(Collectors.toList());
    }


    // UPDATE  
    public OrderResponse updateOrder(String orderId, OrderRequest request) {
        log.info("Updating order with ID: {} and request: {}", orderId, request);
        
        Orders order = orderRepository.findById(orderId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
            
        orderMapper.updateOrder(order, request);
        Orders updatedOrder = orderRepository.save(order);
        updatedOrder.setUpdatedAt(new java.util.Date());

        OrderResponse response = orderMapper.toOrderResponse(updatedOrder);
        response.setUserId(extractUserIdFromJwt());

        return response;
    }

    // DELETE
    public void deleteOrder(String orderId) {
        log.info("Deleting order with ID: {}", orderId);
        if (!orderRepository.existsById(orderId)) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }
        orderRepository.deleteById(orderId);
    }

    private String extractUserIdFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("userId");
        }
        throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
}