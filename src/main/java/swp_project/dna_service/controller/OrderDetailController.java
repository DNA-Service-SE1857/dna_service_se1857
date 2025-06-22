package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.OrderDetailRequest;
import swp_project.dna_service.dto.response.OrderDetailResponse;
import swp_project.dna_service.entity.OrderDetail;
import swp_project.dna_service.service.OrderDetailService;

import java.util.List;

@RestController
@RequestMapping("/order-details")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailController {

    OrderDetailService orderDetailService;


    // CREATE
    @PostMapping("/{orderId}/{serviceId}")
    public ApiResponse<OrderDetailResponse> createOrderDetail(
            @RequestBody OrderDetailRequest orderDetail,
            @PathVariable String orderId,
            @PathVariable String serviceId) {

        OrderDetailResponse response = orderDetailService.createOrderDetail(orderDetail, orderId, serviceId);
        return ApiResponse.<OrderDetailResponse>builder()
                .code(200)
                .message("Order detail created successfully")
                .result(response)
                .build();
    }

    // READ - Get by orderId
    @GetMapping("/{serviceId}")
    public ApiResponse<List<OrderDetailResponse>> getOrderDetail(
            @PathVariable String serviceId) {

        List<OrderDetailResponse> response = orderDetailService.getAllByServiceID(serviceId);
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .code(200)
                .message("Order detail fetched successfully")
                .result(response)
                .build();
    }

    // READ - Get all by orderId
    @GetMapping("/{orderId}/all")
    public ApiResponse<List<OrderDetailResponse>> getAllByOrderId(@PathVariable String orderId) {
        List<OrderDetailResponse> responseList = orderDetailService.getAllByOrderId(orderId);
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .code(200)
                .message("Order details fetched successfully")
                .result(responseList)
                .build();
    }

    // UPDATE
    @PutMapping("/{orderDetailId}")
    public ApiResponse<OrderDetailResponse> updateOrderDetail(
            @RequestBody OrderDetailRequest request,
            @PathVariable String orderDetailId) {

        OrderDetailResponse updated = orderDetailService.updateOrderDetail(request, orderDetailId);
        return ApiResponse.<OrderDetailResponse>builder()
                .code(200)
                .message("Order detail updated successfully")
                .result(updated)
                .build();
    }

    // DELETE
    @DeleteMapping("/{orderDetailId}")
    public ApiResponse<String> deleteOrderDetail(@PathVariable String orderDetailId) {
        orderDetailService.deleteOrderDetail(orderDetailId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Order detail deleted successfully")
                .result("Deleted")
                .build();
    }
}
