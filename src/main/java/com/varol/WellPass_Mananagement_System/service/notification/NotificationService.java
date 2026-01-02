package com.varol.WellPass_Mananagement_System.service.notification;

import java.util.List;

import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.notification.NotificationResponse;
import com.varol.WellPass_Mananagement_System.model.notification.Notification;
import com.varol.WellPass_Mananagement_System.model.notification.NotificationType;

public interface NotificationService {
    
    Notification createNotification(Long userId, String title, String message, NotificationType type);
    
    PageResponse<NotificationResponse> getUserNotifications(Long userId, Integer page, Integer size);
    
    List<NotificationResponse> getUnreadNotifications(Long userId);
    
    void markAsRead(Long notificationId, Long userId);
    
    void markAllAsRead(Long userId);
    
    Long getUnreadCount(Long userId);
    
    void deleteOldNotifications();
}