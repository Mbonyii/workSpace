package com.varol.WellPass_Mananagement_System.dtos.response.employee;

import java.time.LocalDateTime;

import com.varol.WellPass_Mananagement_System.model.user.EmployeeStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;

    private String employeeId;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String department;

    private String profilePictureUrl;

    private EmployeeStatus status;

    private Long companyId;

    private String companyName;

    private LocalDateTime createdAt;
}
