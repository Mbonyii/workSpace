package com.varol.WellPass_Mananagement_System.service.attendance;

import java.time.LocalDateTime;
import java.util.List;

import com.varol.WellPass_Mananagement_System.dtos.request.attendance.AttendanceFilterRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.attendance.CheckInRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.attendance.CheckOutRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.AttendanceResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;

public interface AttendanceService {
    
    AttendanceResponse checkIn(CheckInRequest request);
    
    AttendanceResponse checkOut(CheckOutRequest request);
    
    AttendanceResponse getAttendanceById(Long attendanceId);
    
    PageResponse<AttendanceResponse> getAttendanceHistory(AttendanceFilterRequest filter);
    
    List<AttendanceResponse> getAttendanceByDateRange(Long companyId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<AttendanceResponse> getEmployeeAttendanceHistory(Long employeeId);
}
