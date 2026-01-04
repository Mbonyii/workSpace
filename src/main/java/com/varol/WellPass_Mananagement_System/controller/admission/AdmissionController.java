package com.varol.WellPass_Mananagement_System.controller.admission;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.request.admission.AdmitEmployeeRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.admission.OTPVerificationRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.admission.AdmissionResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.service.admission.AdmissionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admission")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;

    @PostMapping("/initiate")
    @PreAuthorize("hasAnyRole('RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<AdmissionResponse>> initiateAdmission(
            @Valid @RequestBody AdmitEmployeeRequest request) {
        
        AdmissionResponse response = admissionService.initiateAdmission(request);
        return ResponseEntity.ok(ApiResponse.success("Employee details retrieved. OTP sent.", response));
    }

    @PostMapping("/verify")
    @PreAuthorize("hasAnyRole('RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<AdmissionResponse>> verifyAndAdmit(
            @Valid @RequestBody OTPVerificationRequest request) {
        
        AdmissionResponse response = admissionService.verifyAndAdmit(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Access granted. Attendance logged.", response));
    }

    @GetMapping("/{attendanceId}")
    @PreAuthorize("hasAnyRole('RECEPTIONIST', 'SERVICE_PROVIDER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<AdmissionResponse>> getAdmissionDetails(
            @PathVariable Long attendanceId) {
        
        AdmissionResponse response = admissionService.getAdmissionDetails(attendanceId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}