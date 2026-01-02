package com.varol.WellPass_Mananagement_System.dtos.request.employee;

import com.varol.WellPass_Mananagement_System.model.user.EmployeeStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFilterRequest {

    private Long companyId;

    private String department;

    private EmployeeStatus status;

    private String searchQuery;

    private Integer page = 0;

    private Integer size = 20;

    private String sortBy = "createdAt";

    private String sortDirection = "DESC";
}






