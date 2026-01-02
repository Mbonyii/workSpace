package com.varol.WellPass_Mananagement_System.service.attendance;

import java.time.LocalDateTime;

import com.varol.WellPass_Mananagement_System.dtos.response.attendance.AttendanceAnalyticsResponse;

public interface AttendanceAnalyticsService {
    
    AttendanceAnalyticsResponse getAnalytics(Long companyId, LocalDateTime startDate, LocalDateTime endDate);
    
    AttendanceAnalyticsResponse getServiceProviderAnalytics(Long serviceProviderId, LocalDateTime startDate, LocalDateTime endDate);
}
