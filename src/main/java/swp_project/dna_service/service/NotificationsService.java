package swp_project.dna_service.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import swp_project.dna_service.dto.request.NotificationsRequest;
import swp_project.dna_service.dto.response.NotificationsResponse;
import swp_project.dna_service.entity.Notifications;
import swp_project.dna_service.entity.User;
import swp_project.dna_service.exception.AppException;
import swp_project.dna_service.exception.ErrorCode;
import swp_project.dna_service.mapper.NotificationsMapper;
import swp_project.dna_service.repository.NotificationsRepository;
import swp_project.dna_service.repository.UserRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationsService {

    NotificationsRepository notificationsRepository ;
    UserRepository userRepository ;
    NotificationsMapper notificationsMapper ;

    public NotificationsResponse createNotifical(NotificationsRequest request) {
        log.info("Creating notification with request: {}", request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new AppException(ErrorCode.USER_NOT_FOUND);
                });

        Notifications notification = notificationsMapper.toNotifications(request);
        notification.setUser(user);
        notification.setIs_read(false);
        notification.setCreatedAt(new Date());


        Notifications savedNotification = notificationsRepository.save(notification);
        log.info("Created notification with ID: {}", savedNotification.getId());
        NotificationsResponse  response = notificationsMapper.toNotificationsResponse(savedNotification);
        response.setUserId(userId);

        return response;
    }


    public NotificationsResponse updateNotification(String notificationId, NotificationsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new AppException(ErrorCode.USER_NOT_FOUND);
                });

        log.info("Updating notification ID: {} for user ID: {}", notificationId, userId);

        Notifications existingNotification = notificationsRepository.findByIdAndUser_Id(notificationId, userId)
                .orElseThrow(() -> {
                    log.error("Notification not found with ID: {} and user ID: {}", notificationId, userId);
                    return new AppException(ErrorCode.NOTIFICATION_NOT_FOUND);
                });

        existingNotification.setTitle(request.getTitle());
        existingNotification.setMessage(request.getMessage());
        existingNotification.setType(request.getType());
        existingNotification.setIs_read(request.getIs_read());

        Notifications updatedNotification = notificationsRepository.save(existingNotification);
        log.info("Updated notification ID: {}", updatedNotification.getId());

        return notificationsMapper.toNotificationsResponse(updatedNotification);
    }


    public List<NotificationsResponse> getAllNotificationsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        log.info("Fetching all notifications for user ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            log.error("User not found with ID: {}", userId);
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        List<Notifications> notifications = notificationsRepository.findAllByUser_Id(userId);

        if (notifications.isEmpty()) {
            log.info("No notifications found for user ID: {}", userId);
            return Collections.emptyList();
        }

        return notifications.stream()
                .map(notification -> {
                    NotificationsResponse response = notificationsMapper.toNotificationsResponse(notification);
                    response.setUserId(userId);
                    return response;
                })
                .collect(Collectors.toList());

    }

    public NotificationsResponse getNotifications(String notificationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId;
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            userId = jwt.getClaimAsString("userId");
            log.debug("Extracted user ID from JWT: {}", userId);
        } else {
            log.error("Unexpected principal type: {}", authentication.getPrincipal().getClass());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Notifications notification = notificationsRepository.findById(notificationId)
                .orElseThrow(() -> {
                    log.error("Notification not found with ID: {}", notificationId);
                    return new AppException(ErrorCode.NOTIFICATION_NOT_FOUND);
                });

        if (!notification.getUser().getId().equals(userId) &&
                !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            log.error("User {} is not authorized to view notification {}", userId, notificationId);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (!notification.getIs_read()) {
            notification.setIs_read(true);
            notification = notificationsRepository.save(notification);
            log.info("Marked notification ID: {} as read", notificationId);
        }
        NotificationsResponse response = notificationsMapper.toNotificationsResponse(notification);
        response.setUserId(userId);

        return response;
    }




    public void deleteNotifications(String notificationsId) {
        Notifications notification = notificationsRepository.findById(notificationsId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String authenticatedUserId = jwt.getClaimAsString("userId");

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_STAFF"));
        boolean isOwner = notification.getUser().getId().equals(authenticatedUserId);

        if (!isAdmin && !isOwner) {
            log.error("User {} is not authorized to delete notification {}", authenticatedUserId, notificationsId);
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        try {
            notificationsRepository.delete(notification);
            log.info("Notification with ID: {} deleted successfully", notificationsId);
        } catch (Exception e) {
            log.error("Error deleting notification with id {}: {}", notificationsId, e.getMessage());
            throw new AppException(ErrorCode.NOTIFICATION_NOT_FOUND);  // Sử dụng AppException thay vì ServiceException
        }
    }


}