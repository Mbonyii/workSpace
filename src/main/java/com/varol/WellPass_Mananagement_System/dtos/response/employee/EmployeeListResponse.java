package com.varol.WellPass_Mananagement_System.dtos.response.employee;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListResponse {

    private List<EmployeeResponse> employees;

    private Long totalElements;

    private Integer totalPages;

    private Integer currentPage;

    private Integer pageSize;
}