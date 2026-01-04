package com.varol.WellPass_Mananagement_System.controller.attendance;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.response.attendance.AttendanceAnalyticsResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.MonthlyReportResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.TodayAttendanceResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.service.attendance.AttendanceAnalyticsService;
import com.varol.WellPass_Mananagement_System.service.attendance.AttendanceReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final AttendanceReportService reportService;
    private final AttendanceAnalyticsService analyticsService;

    @GetMapping("/today/service-provider/{serviceProviderId}")
    @PreAuthorize("hasAnyRole('RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<TodayAttendanceResponse>> getTodayAttendance(
            @PathVariable Long serviceProviderId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        LocalDate reportDate = date != null ? date : LocalDate.now();
        TodayAttendanceResponse response = reportService.getTodayAttendance(serviceProviderId, reportDate);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/today/company/{companyId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<TodayAttendanceResponse>> getCompanyTodayAttendance(
            @PathVariable Long companyId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        LocalDate reportDate = date != null ? date : LocalDate.now();
        TodayAttendanceResponse response = reportService.getCompanyTodayAttendance(companyId, reportDate);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/monthly/company/{companyId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<MonthlyReportResponse>> getMonthlyReport(
            @PathVariable Long companyId,
            @RequestParam Integer year,
            @RequestParam Integer month) {
        
        MonthlyReportResponse response = reportService.getMonthlyReport(companyId, year, month);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/analytics/company/{companyId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<AttendanceAnalyticsResponse>> getAnalytics(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime endDate) {
        
        AttendanceAnalyticsResponse response = analyticsService.getAnalytics(companyId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/export/today/csv/service-provider/{serviceProviderId}")
    @PreAuthorize("hasAnyRole('RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<byte[]> exportTodayAttendanceToCsv(
            @PathVariable Long serviceProviderId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        LocalDate reportDate = date != null ? date : LocalDate.now();
        byte[] csvData = reportService.exportTodayAttendanceToCsv(serviceProviderId, reportDate);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=today_attendance.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvData);
    }

    @GetMapping("/export/monthly/pdf/company/{companyId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<byte[]> exportMonthlyReportToPdf(
            @PathVariable Long companyId,
            @RequestParam Integer year,
            @RequestParam Integer month) {
        
        byte[] pdfData = reportService.exportMonthlyReportToPdf(companyId, year, month);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=monthly_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }
}