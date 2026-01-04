package com.varol.WellPass_Mananagement_System.controller.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.request.employee.BulkEmployeeUploadRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeFilterRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeRegistrationRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeUpdateRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeProfileResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeStatsResponse;
import com.varol.WellPass_Mananagement_System.service.employee.EmployeeManagementService;
import com.varol.WellPass_Mananagement_System.service.employee.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeManagementService employeeManagementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> registerEmployee(
            @Valid @RequestBody EmployeeRegistrationRequest request) {
        
        EmployeeResponse response = employeeService.registerEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Employee registered successfully", response));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<EmployeeResponse>>> bulkUploadEmployees(
            @Valid @RequestBody BulkEmployeeUploadRequest request) {
        
        java.util.List<EmployeeResponse> responses = employeeManagementService.bulkUploadEmployees(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Bulk upload completed", responses));
    }

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> getEmployeeProfile(
            @PathVariable String employeeId) {
        
        EmployeeProfileResponse response = employeeService.getEmployeeProfile(employeeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeUpdateRequest request) {
        
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", response));
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<PageResponse<EmployeeResponse>>> filterEmployees(
            @RequestBody EmployeeFilterRequest filter) {
        
        PageResponse<EmployeeResponse> response = employeeManagementService.getEmployeesByCompany(
                filter.getCompanyId(), filter);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/company/{companyId}/stats")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<EmployeeStatsResponse>> getCompanyEmployeeStats(
            @PathVariable Long companyId) {
        
        EmployeeStatsResponse response = employeeManagementService.getCompanyEmployeeStats(companyId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{employeeId}/deactivate")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateEmployee(@PathVariable String employeeId) {
        employeeService.deactivateEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.success("Employee deactivated successfully", null));
    }

    @PutMapping("/{employeeId}/activate")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateEmployee(@PathVariable String employeeId) {
        employeeService.activateEmployee(employeeId);
        return ResponseEntity.ok(ApiResponse.success("Employee activated successfully", null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deleted successfully", null));
    }
}