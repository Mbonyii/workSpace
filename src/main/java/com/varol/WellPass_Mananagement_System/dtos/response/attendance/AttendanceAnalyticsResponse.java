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
public class AttendanceAnalyticsResponse {

    private Map<String, Integer> visitsByDay;

    private Map<String, BigDecimal> revenueByDay;

    private Map<String, Integer> visitsByService;

    private Map<String, Integer> visitsByDepartment;

    private List<PeakHour> peakHours;

    private BigDecimal averageVisitCost;

    private Integer totalUniqueEmployees;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeakHour {
        private Integer hour;
        private Integer visitCount;
    }
}