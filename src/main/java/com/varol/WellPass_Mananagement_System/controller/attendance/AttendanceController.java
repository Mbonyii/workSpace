package com.varol.WellPass_Mananagement_System.controller.attendance;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.request.attendance.AttendanceFilterRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.attendance.CheckInRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.attendance.CheckOutRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.AttendanceResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;
import com.varol.WellPass_Mananagement_System.service.attendance.AttendanceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkIn(
            @Valid @RequestBody CheckInRequest request) {
        
        AttendanceResponse response = attendanceService.checkIn(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Check-in successful", response));
    }

    @PostMapping("/check-out")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> checkOut(
            @Valid @RequestBody CheckOutRequest request) {
        
        AttendanceResponse response = attendanceService.checkOut(request);
        return ResponseEntity.ok(ApiResponse.success("Check-out successful", response));
    }

    @GetMapping("/{attendanceId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<AttendanceResponse>> getAttendanceById(
            @PathVariable Long attendanceId) {
        
        AttendanceResponse response = attendanceService.getAttendanceById(attendanceId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PageResponse<AttendanceResponse>>> getAttendanceHistory(
            @RequestBody AttendanceFilterRequest filter) {
        
        PageResponse<AttendanceResponse> response = attendanceService.getAttendanceHistory(filter);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/company/{companyId}/date-range")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<AttendanceResponse>>> getAttendanceByDateRange(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        
        java.util.List<AttendanceResponse> responses = attendanceService.getAttendanceByDateRange(
                companyId, startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/employee/{employeeId}/history")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<java.util.List<AttendanceResponse>>> getEmployeeAttendanceHistory(
            @PathVariable Long employeeId) {
        
        java.util.List<AttendanceResponse> responses = attendanceService.getEmployeeAttendanceHistory(employeeId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}