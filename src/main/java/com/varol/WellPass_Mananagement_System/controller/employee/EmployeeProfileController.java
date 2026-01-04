package com.varol.WellPass_Mananagement_System.controller.employee;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeProfileResponse;
import com.varol.WellPass_Mananagement_System.service.employee.EmployeeService;
import com.varol.WellPass_Mananagement_System.service.storage.FileStorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee-profile")
@RequiredArgsConstructor
public class EmployeeProfileController {

    private final EmployeeService employeeService;
    private final FileStorageService fileStorageService;

    @GetMapping("/{employeeId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<EmployeeProfileResponse>> getEmployeeProfile(
            @PathVariable String employeeId) {
        
        EmployeeProfileResponse response = employeeService.getEmployeeProfile(employeeId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{employeeId}/profile-picture")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<String>> uploadProfilePicture(
            @PathVariable String employeeId,
            @RequestParam("file") MultipartFile file) {
        
        String fileUrl = fileStorageService.storeProfilePicture(file, employeeId);
        return ResponseEntity.ok(ApiResponse.success("Profile picture uploaded successfully", fileUrl));
    }
}