package com.varol.WellPass_Mananagement_System.implementation.attendance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.varol.WellPass_Mananagement_System.dtos.response.attendance.AttendanceAnalyticsResponse;
import com.varol.WellPass_Mananagement_System.model.attendance.Attendance;
import com.varol.WellPass_Mananagement_System.repository.attendance.AttendanceRepository;
import com.varol.WellPass_Mananagement_System.service.attendance.AttendanceAnalyticsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceAnalyticsServiceImpl implements AttendanceAnalyticsService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public AttendanceAnalyticsResponse getAnalytics(Long companyId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Attendance> attendances = attendanceRepository.findByCompanyIdAndDateRange(companyId, startDate, endDate);

        AttendanceAnalyticsResponse response = new AttendanceAnalyticsResponse();

        Map<String, Integer> visitsByDay = new HashMap<>();
        Map<String, BigDecimal> revenueByDay = new HashMap<>();
        attendances.forEach(a -> {
            String day = a.getCheckInTime().toLocalDate().toString();
            visitsByDay.put(day, visitsByDay.getOrDefault(day, 0) + 1);
            revenueByDay.put(day, revenueByDay.getOrDefault(day, BigDecimal.ZERO).add(a.getPrice()));
        });
        response.setVisitsByDay(visitsByDay);
        response.setRevenueByDay(revenueByDay);

        Map<String, Integer> visitsByService = attendances.stream()
                .collect(Collectors.groupingBy(
                    a -> a.getService().getServiceName(),
                    Collectors.summingInt(a -> 1)
                ));
        response.setVisitsByService(visitsByService);

        Map<String, Integer> visitsByDepartment = attendances.stream()
                .collect(Collectors.groupingBy(
                    a -> a.getEmployee().getDepartment(),
                    Collectors.summingInt(a -> 1)
                ));
        response.setVisitsByDepartment(visitsByDepartment);

        BigDecimal totalRevenue = attendances.stream()
                .map(Attendance::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal averageVisitCost = attendances.isEmpty() 
            ? BigDecimal.ZERO 
            : totalRevenue.divide(BigDecimal.valueOf(attendances.size()), 2, RoundingMode.HALF_UP);
        response.setAverageVisitCost(averageVisitCost);

        long uniqueEmployees = attendances.stream()
                .map(a -> a.getEmployee().getId())
                .distinct()
                .count();
        response.setTotalUniqueEmployees((int) uniqueEmployees);

        return response;
    }

    @Override
    public AttendanceAnalyticsResponse getServiceProviderAnalytics(Long serviceProviderId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Attendance> attendances = attendanceRepository.findByServiceProviderIdAndDateRange(serviceProviderId, startDate, endDate);

        AttendanceAnalyticsResponse response = new AttendanceAnalyticsResponse();

        Map<String, Integer> visitsByDay = new HashMap<>();
        attendances.forEach(a -> {
            String day = a.getCheckInTime().toLocalDate().toString();
            visitsByDay.put(day, visitsByDay.getOrDefault(day, 0) + 1);
        });
        response.setVisitsByDay(visitsByDay);

        return response;
    }
}