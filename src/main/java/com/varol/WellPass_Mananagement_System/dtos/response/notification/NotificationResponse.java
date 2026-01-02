package com.varol.WellPass_Mananagement_System.dtos.response.notification;

import java.time.LocalDateTime;

import com.varol.WellPass_Mananagement_System.model.notification.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private String title;

    private String message;

    private NotificationType type;

    private Boolean isRead;

    private LocalDateTime readAt;

    private LocalDateTime createdAt;
}
