package com.varol.WellPass_Mananagement_System.dtos.request.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRegistrationRequest {

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotBlank(message = "Department is required")
    private String department;

    private String profilePictureUrl;
}






