package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.OrderParticipantsRequest;
import swp_project.dna_service.dto.response.OrderParticipantsResponse;
import swp_project.dna_service.entity.OrderParticipants;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.mapper.OrderParticipantsMapper;
import swp_project.dna_service.repository.OrderParticipantsRepository;
import swp_project.dna_service.repository.OrderRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderParticipantsService {

    OrderParticipantsRepository orderParticipantsRepository;
    OrderParticipantsMapper orderParticipantsMapper;
    OrderRepository orderRepository;

    // CREATE
    public OrderParticipantsResponse createOrderParticipant(OrderParticipantsRequest request, String orderId) {
        log.info("Creating OrderParticipant with request: {}", request);

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        var entity = orderParticipantsMapper.toOrderParticipants(request);
        entity.setOrders(order);
        entity = orderParticipantsRepository.save(entity);

        var response = orderParticipantsMapper.toOrderParticipantsResponse(entity);
        response.setOrder_id(orderId);
        log.info("Created OrderParticipant response: {}", response);
        return response;
    }

    // READ - Get by ID
    public OrderParticipantsResponse getOrderParticipantById(String id) {
        var entity = orderParticipantsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderParticipant not found with ID: " + id));
        var response = orderParticipantsMapper.toOrderParticipantsResponse(entity);
        response.setOrder_id(entity.getOrders().getId());
        return response;
    }

    // READ - Get all
    public List<OrderParticipantsResponse> getAllOrderParticipants() {
        return orderParticipantsRepository.findAll()
                .stream()
                .map(entity -> {
                    var res = orderParticipantsMapper.toOrderParticipantsResponse(entity);
                    res.setOrder_id(entity.getOrders().getId());
                    return res;
                })
                .toList();
    }

    // READ - Get by orderId
    public List<OrderParticipantsResponse> getByOrderId(String orderId) {
        return orderParticipantsRepository.findByOrders_Id(orderId)
                .stream()
                .map(entity -> {
                    var res = orderParticipantsMapper.toOrderParticipantsResponse(entity);
                    res.setOrder_id(orderId);
                    return res;
                })
                .toList();
    }

    // UPDATE
    public OrderParticipantsResponse updateOrderParticipant(String id, OrderParticipantsRequest request) {
        var existing = orderParticipantsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderParticipant not found with ID: " + id));

        orderParticipantsMapper.updateOrderParticipants(existing, request); // bạn cần viết hàm này trong mapper
        existing = orderParticipantsRepository.save(existing);

        var response = orderParticipantsMapper.toOrderParticipantsResponse(existing);
        response.setOrder_id(existing.getOrders().getId());
        return response;
    }

    // DELETE
    public void deleteOrderParticipant(String id) {
        var entity = orderParticipantsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderParticipant not found with ID: " + id));
        orderParticipantsRepository.delete(entity);
    }
}
