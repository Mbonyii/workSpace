package com.varol.WellPass_Mananagement_System.service.employee;

import java.util.List;

import com.varol.WellPass_Mananagement_System.dtos.request.employee.BulkEmployeeUploadRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeFilterRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeStatsResponse;

public interface EmployeeManagementService {
    
    PageResponse<EmployeeResponse> getEmployeesByCompany(Long companyId, EmployeeFilterRequest filter);
    
    List<EmployeeResponse> bulkUploadEmployees(BulkEmployeeUploadRequest request);
    
    EmployeeStatsResponse getCompanyEmployeeStats(Long companyId);
    
    List<EmployeeResponse> searchEmployees(Long companyId, String query);
    
    byte[] exportEmployeesToCsv(Long companyId);
}
