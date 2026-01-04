package com.varol.WellPass_Mananagement_System.implementation.attendance;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.varol.WellPass_Mananagement_System.dtos.response.attendance.AttendanceResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.MonthlyReportResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.TodayAttendanceResponse;
import com.varol.WellPass_Mananagement_System.model.attendance.Attendance;
import com.varol.WellPass_Mananagement_System.repository.attendance.AttendanceRepository;
import com.varol.WellPass_Mananagement_System.service.attendance.AttendanceReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceReportServiceImpl implements AttendanceReportService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public TodayAttendanceResponse getTodayAttendance(Long serviceProviderId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Attendance> attendances = attendanceRepository.findByServiceProviderIdAndDateRange(
            serviceProviderId,
            startOfDay,
            endOfDay
        );

        TodayAttendanceResponse response = new TodayAttendanceResponse();
        response.setDate(date);
        response.setTotalEmployeesAdmitted(attendances.size());
        
        BigDecimal totalRevenue = attendances.stream()
                .map(Attendance::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalRevenueToday(totalRevenue);

        List<AttendanceResponse> recentAdmissions = attendances.stream()
                .sorted((a1, a2) -> a2.getCheckInTime().compareTo(a1.getCheckInTime()))
                .limit(10)
                .map(this::mapToAttendanceResponse)
                .collect(Collectors.toList());
        response.setRecentAdmissions(recentAdmissions);

        Map<String, TodayAttendanceResponse.CompanySummary> byCompany = new HashMap<>();
        attendances.stream()
                .collect(Collectors.groupingBy(a -> a.getCompany().getCompanyName()))
                .forEach((company, list) -> {
                    BigDecimal revenue = list.stream()
                            .map(Attendance::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    byCompany.put(company, new TodayAttendanceResponse.CompanySummary(list.size(), revenue));
                });
        response.setByCompany(byCompany);

        Map<String, TodayAttendanceResponse.ServiceSummary> byService = new HashMap<>();
        attendances.stream()
                .collect(Collectors.groupingBy(a -> a.getService().getServiceName()))
                .forEach((service, list) -> {
                    BigDecimal revenue = list.stream()
                            .map(Attendance::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    byService.put(service, new TodayAttendanceResponse.ServiceSummary(list.size(), revenue));
                });
        response.setByService(byService);

        return response;
    }

    @Override
    public TodayAttendanceResponse getCompanyTodayAttendance(Long companyId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Attendance> attendances = attendanceRepository.findByCompanyIdAndDateRange(
            companyId,
            startOfDay,
            endOfDay
        );

        TodayAttendanceResponse response = new TodayAttendanceResponse();
        response.setDate(date);
        response.setTotalEmployeesAdmitted(attendances.size());

        BigDecimal totalRevenue = attendances.stream()
                .map(Attendance::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalRevenueToday(totalRevenue);

        List<AttendanceResponse> recentAdmissions = attendances.stream()
                .sorted((a1, a2) -> a2.getCheckInTime().compareTo(a1.getCheckInTime()))
                .map(this::mapToAttendanceResponse)
                .collect(Collectors.toList());
        response.setRecentAdmissions(recentAdmissions);

        return response;
    }

    @Override
    public MonthlyReportResponse getMonthlyReport(Long companyId, Integer year, Integer month) {
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

        List<Attendance> attendances = attendanceRepository.findByCompanyIdAndDateRange(
            companyId,
            startOfMonth,
            endOfMonth
        );

        MonthlyReportResponse response = new MonthlyReportResponse();
        response.setYear(year);
        response.setMonth(month);
        response.setTotalVisitsThisMonth(attendances.size());

        long uniqueEmployees = attendances.stream()
                .map(a -> a.getEmployee().getId())
                .distinct()
                .count();
        response.setTotalEmployeesUsingServices((int) uniqueEmployees);

        BigDecimal totalCost = attendances.stream()
                .map(Attendance::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setCurrentMonthCost(totalCost);

        return response;
    }

    @Override
    public byte[] exportTodayAttendanceToCsv(Long serviceProviderId, LocalDate date) {
        TodayAttendanceResponse todayData = getTodayAttendance(serviceProviderId, date);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        writer.println("Today's Attendance Report - " + date.format(DateTimeFormatter.ISO_DATE));
        writer.println();
        writer.println("Total Employees Admitted," + todayData.getTotalEmployeesAdmitted());
        writer.println("Total Revenue Today," + todayData.getTotalRevenueToday());
        writer.println();
        writer.println("Time,Employee ID,Employee Name,Company,Department,Service,Price");

        for (AttendanceResponse attendance : todayData.getRecentAdmissions()) {
            writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                attendance.getCheckInTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                attendance.getEmployeeId(),
                attendance.getEmployeeName(),
                attendance.getCompanyName(),
                attendance.getDepartment(),
                attendance.getServiceName(),
                attendance.getPrice()
            );
        }

        writer.flush();
        return outputStream.toByteArray();
    }

    @Override
    public byte[] exportMonthlyReportToPdf(Long companyId, Integer year, Integer month) {
        return new byte[0];
    }

    private AttendanceResponse mapToAttendanceResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setId(attendance.getId());
        response.setEmployeeId(attendance.getEmployee().getEmployeeId());
        response.setEmployeeName(attendance.getEmployee().getFullName());
        response.setProfilePictureUrl(attendance.getEmployee().getProfilePictureUrl());
        response.setCompanyName(attendance.getCompany().getCompanyName());
        response.setDepartment(attendance.getEmployee().getDepartment());
        response.setServiceName(attendance.getService().getServiceName());
        response.setServiceProviderName(attendance.getServiceProvider().getProviderName());
        response.setPrice(attendance.getPrice());
        response.setCheckInTime(attendance.getCheckInTime());
        response.setCheckOutTime(attendance.getCheckOutTime());
        response.setStatus(attendance.getStatus());
        response.setVerifiedByOtp(attendance.getVerifiedByOtp());
        return response;
    }
}