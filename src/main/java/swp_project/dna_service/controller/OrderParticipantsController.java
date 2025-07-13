package swp_project.dna_service.controller;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.OrderParticipantsRequest;
import swp_project.dna_service.dto.response.OrderParticipantsResponse;
import swp_project.dna_service.entity.OrderParticipants;
import swp_project.dna_service.service.OrderParticipantsService;

import java.util.List;

@RestController
@RequestMapping("/OrderParticipants")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderParticipantsController {

    OrderParticipantsService orderParticipantsService;


    @PostMapping("/{orderId}")
    public ApiResponse<OrderParticipantsResponse> createOrderParticipants(
            @RequestBody OrderParticipantsRequest request,
            @PathVariable String orderId) {

        OrderParticipantsResponse response = orderParticipantsService.createOrderParticipant(request, orderId);

        return ApiResponse.<OrderParticipantsResponse>builder()
                .code(200)
                .message("Order participants created successfully")
                .result(response)
                .build();
    }


    @GetMapping("/{id}")
    public ApiResponse<OrderParticipantsResponse> getOrderParticipantById(@PathVariable String id) {
        OrderParticipantsResponse response = orderParticipantsService.getOrderParticipantById(id);

        return ApiResponse.<OrderParticipantsResponse>builder()
                .code(200)
                .message("Order participant fetched successfully")
                .result(response)
                .build();
    }


    @GetMapping
    public ApiResponse<List<OrderParticipantsResponse>> getAllOrderParticipants() {
        List<OrderParticipantsResponse> responses = orderParticipantsService.getAllOrderParticipants();

        return ApiResponse.<List<OrderParticipantsResponse>>builder()
                .code(200)
                .message("All order participants fetched successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<List<OrderParticipantsResponse>> getOrderParticipantsByOrderId(@PathVariable String orderId) {
        List<OrderParticipantsResponse> responses = orderParticipantsService.getByOrderId(orderId);

        return ApiResponse.<List<OrderParticipantsResponse>>builder()
                .code(200)
                .message("Order participants fetched by order ID successfully")
                .result(responses)
                .build();
    }


    @PutMapping("/{id}")
    public ApiResponse<OrderParticipantsResponse> updateOrderParticipant(
            @PathVariable String id,
            @RequestBody OrderParticipantsRequest request) {

        OrderParticipantsResponse response = orderParticipantsService.updateOrderParticipant(id, request);

        return ApiResponse.<OrderParticipantsResponse>builder()
                .code(200)
                .message("Order participant updated successfully")
                .result(response)
                .build();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteOrderParticipant(@PathVariable String id) {
        orderParticipantsService.deleteOrderParticipant(id);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Order participant deleted successfully")
                .build();
    }
}
