package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.OrderRequest;
import swp_project.dna_service.dto.response.OrderResponse;
import swp_project.dna_service.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;
    private final RestClient.Builder builder;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order created successfully")
                .result(response)
                .build();
    }
    @PostMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@RequestBody OrderRequest request, @PathVariable String orderId) {
        OrderResponse response = orderService.updateOrder(orderId , request);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order updated successfully")
                .result(response)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable String userId) {
        List<OrderResponse> responses = orderService.getOrdersByUserId(userId) ;
        return ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .message("Order updated successfully")
                .result(responses)
                .build();
    }


    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable String orderId) {
        OrderResponse response = orderService.getOrderById(orderId);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order retrieved successfully")
                .result(response)
                .build();
    }
    @GetMapping("/all")
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        log.info("Received get all orders request");
        List<OrderResponse> responses = orderService.getAllOrders();
        return ApiResponse.<List<OrderResponse>>builder()
                .code(200)
                .message("Orders retrieved successfully")
                .result(responses)
                .build();
    }

    @PutMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(
            @PathVariable String orderId,
            @RequestBody OrderRequest request) {
        log.info("Received update order request for ID: {}, request: {}", orderId, request);
        OrderResponse response = orderService.updateOrder(orderId, request);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Order updated successfully")
                .result(response)
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<Void> deleteOrder(@PathVariable String orderId) {
        log.info("Received delete order request for ID: {}", orderId);
        orderService.deleteOrder(orderId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Order deleted successfully")
                .build();
    }


}
