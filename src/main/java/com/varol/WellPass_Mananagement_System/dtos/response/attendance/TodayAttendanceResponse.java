package com.varol.WellPass_Mananagement_System.dtos.response.attendance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayAttendanceResponse {

    private LocalDate date;

    private Integer totalEmployeesAdmitted;

    private BigDecimal totalRevenueToday;

    private List<AttendanceResponse> recentAdmissions;

    private Map<String, CompanySummary> byCompany;

    private Map<String, ServiceSummary> byService;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanySummary {
        private Integer employeeCount;
        private BigDecimal revenue;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceSummary {
        private Integer count;
        private BigDecimal revenue;
    }
}






