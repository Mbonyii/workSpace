package com.varol.WellPass_Mananagement_System.dtos.response.attendance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyReportResponse {

    private Integer year;

    private Integer month;

    private Integer totalEmployeesUsingServices;

    private Integer totalVisitsThisMonth;

    private BigDecimal currentMonthCost;

    private BigDecimal projectedMonthEndCost;

    private Map<String, DepartmentBreakdown> departmentBreakdowns;

    private List<ServiceProviderSummary> serviceProviderBreakdowns;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DepartmentBreakdown {
        private Integer totalVisits;
        private BigDecimal cost;
        private List<TopUser> topUsers;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopUser {
        private String employeeId;
        private String employeeName;
        private Integer visits;
        private String servicesUsed;
        private BigDecimal cost;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceProviderSummary {
        private String providerName;
        private BigDecimal amount;
        private Integer visits;
    }
}







