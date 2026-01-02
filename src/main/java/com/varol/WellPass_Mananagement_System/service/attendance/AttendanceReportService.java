package com.varol.WellPass_Mananagement_System.service.attendance;

import java.time.LocalDate;

import com.varol.WellPass_Mananagement_System.dtos.response.attendance.MonthlyReportResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.TodayAttendanceResponse;

public interface AttendanceReportService {
    
    TodayAttendanceResponse getTodayAttendance(Long serviceProviderId, LocalDate date);
    
    TodayAttendanceResponse getCompanyTodayAttendance(Long companyId, LocalDate date);
    
    MonthlyReportResponse getMonthlyReport(Long companyId, Integer year, Integer month);
    
    byte[] exportTodayAttendanceToCsv(Long serviceProviderId, LocalDate date);
    
    byte[] exportMonthlyReportToPdf(Long companyId, Integer year, Integer month);
}
