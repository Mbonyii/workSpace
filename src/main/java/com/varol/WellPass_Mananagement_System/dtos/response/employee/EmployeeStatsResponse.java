package com.varol.WellPass_Mananagement_System.dtos.response.employee;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeStatsResponse {

    private Long totalEmployees;

    private Long activeEmployees;

    private Long inactiveEmployees;

    private Integer totalVisitsThisMonth;

    private BigDecimal totalSpentThisMonth;

    private Integer averageVisitsPerEmployee;
}






