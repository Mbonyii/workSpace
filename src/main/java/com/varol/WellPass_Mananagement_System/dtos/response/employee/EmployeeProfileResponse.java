package com.varol.WellPass_Mananagement_System.dtos.response.employee;

import java.math.BigDecimal;

import com.varol.WellPass_Mananagement_System.model.user.EmployeeStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfileResponse {

    private Long id;

    private String employeeId;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String department;

    private String profilePictureUrl;

    private EmployeeStatus status;

    private String companyName;

    private String companyCode;

    private Integer totalVisits;

    private BigDecimal totalSpent;
}