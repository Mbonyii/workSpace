package com.varol.WellPass_Mananagement_System.dtos.request.employee;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequest {

    private String fullName;

    private String phoneNumber;

    @Email(message = "Email must be valid")
    private String email;

    private String department;

    private String profilePictureUrl;
}






