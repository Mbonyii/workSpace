package com.varol.WellPass_Mananagement_System.dtos.request.employee;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkEmployeeUploadRequest {

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotEmpty(message = "Employee list cannot be empty")
    @Valid
    private List<EmployeeRegistrationRequest> employees;
}







