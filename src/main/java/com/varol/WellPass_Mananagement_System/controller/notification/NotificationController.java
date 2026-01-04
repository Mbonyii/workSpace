package com.varol.WellPass_Mananagement_System.controller.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.notification.NotificationResponse;
import com.varol.WellPass_Mananagement_System.service.notification.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE', 'RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<NotificationResponse>>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        PageResponse<NotificationResponse> response = notificationService.getUserNotifications(userId, page, size);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/user/{userId}/unread")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE', 'RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<java.util.List<NotificationResponse>>> getUnreadNotifications(
            @PathVariable Long userId) {
        
        java.util.List<NotificationResponse> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success(notifications));
    }

    @GetMapping("/user/{userId}/unread-count")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE', 'RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<Long>> getUnreadCount(@PathVariable Long userId) {
        Long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PutMapping("/{notificationId}/read")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE', 'RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable Long notificationId,
            @RequestParam Long userId) {
        
        notificationService.markAsRead(notificationId, userId);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read", null));
    }

    @PutMapping("/user/{userId}/read-all")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE', 'RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
    }
}