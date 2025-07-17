package swp_project.dna_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import swp_project.dna_service.dto.ApiResponse;
import swp_project.dna_service.dto.request.NotificationsRequest;
import swp_project.dna_service.dto.response.NotificationsResponse;
import swp_project.dna_service.service.NotificationsService;
import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationsController {

    private final NotificationsService notificationsService;


    @PostMapping
    public ApiResponse<NotificationsResponse> createNotification(@RequestBody NotificationsRequest request) {
        NotificationsResponse response = notificationsService.createNotifical(request);
        return ApiResponse.<NotificationsResponse>builder()
                .code(200)
                .message("Notification created successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<NotificationsResponse> updateNotification(
            @PathVariable String notificationId,
            @RequestBody NotificationsRequest request) {
        NotificationsResponse response = notificationsService.updateNotification(notificationId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ApiResponse<List<NotificationsResponse>> getAllNotifications() {

        notificationsService.getAllNotificationsByUserId();
        return ApiResponse.<List<NotificationsResponse>>builder()
                .code(200)
                .message("Get all notifications successfully")
                .result(notificationsService.getAllNotificationsByUserId())
                .build();
    }

    @GetMapping("/{notificationId}")
    public ApiResponse<NotificationsResponse> getNotification(@PathVariable String notificationId) {
        log.info("Getting notification with ID: {}", notificationId);
        NotificationsResponse response = notificationsService.getNotifications(notificationId);
        return ApiResponse.<NotificationsResponse>builder()
                .code(200)
                .message("Notification retrieved successfully")
                .result(response)
                .build();
    }

    @DeleteMapping("/{notificationId}")
    public ApiResponse<Object> deleteNotification(
            @PathVariable String notificationId) {
        notificationsService.deleteNotifications(notificationId);
        return ApiResponse.builder()
                .code(200)
                .message("Delete successfully")
                .build();
    }
}
