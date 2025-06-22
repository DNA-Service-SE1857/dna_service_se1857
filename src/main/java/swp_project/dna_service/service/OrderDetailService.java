package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.OrderDetailRequest;
import swp_project.dna_service.dto.response.OrderDetailResponse;
import swp_project.dna_service.entity.Dna_Service;
import swp_project.dna_service.entity.OrderDetail;
import swp_project.dna_service.entity.Orders;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.OrderDetailMapper;
import swp_project.dna_service.repository.OrderDetailRepository;
import swp_project.dna_service.repository.OrderRepository;
import swp_project.dna_service.repository.ServiceRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {

    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;
    OrderRepository orderRepository;
    ServiceRepository serviceRepository;

    public OrderDetailResponse createOrderDetail(OrderDetailRequest request, String orderId , String serviceId) {

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        Dna_Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(request);

        orderDetail.setDna_service(service);
        orderDetail.setOrders(order);
        orderDetail.setCreatedAt(new java.util.Date());
        orderDetail.setUpdatedAt(new java.util.Date());
        orderDetailRepository.save(orderDetail);

        OrderDetailResponse orderDetailResponse = orderDetailMapper.toResponse(orderDetail);
        orderDetailResponse.setOrderId(order.getId());
        orderDetailResponse.setDnaServiceId(service.getId());

        return orderDetailResponse;
    }

    // READ all by orderId
    public List<OrderDetailResponse> getByOrderId(String orderId) {
        List<OrderDetail> details = orderDetailRepository.findByOrders_Id(orderId);
        if (details.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND);
        }
        return details.stream()
                .map(orderDetailMapper::toResponse)
                .collect(Collectors.toList());
    }

    // READ all by orderId
    public List<OrderDetailResponse> getAllByOrderId(String orderId) {
        return orderDetailRepository.findByOrders_Id(orderId)
                .stream()
                .map(orderDetailMapper::toResponse)
                .collect(Collectors.toList());
    }

    // UPDATE
    public OrderDetailResponse updateOrderDetail(OrderDetailRequest request, String orderDetailId) {
        OrderDetail existing = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));

        orderDetailMapper.updateOrderDetail(existing, request);
        existing.setUpdatedAt(new Date());

        OrderDetail saved = orderDetailRepository.save(existing);
        return orderDetailMapper.toResponse(saved);
    }

    // DELETE
    public void deleteOrderDetail(String orderDetailId) {
    OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
            .orElseThrow(() -> new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND));
        orderDetailRepository.delete(orderDetail);
    }



}
