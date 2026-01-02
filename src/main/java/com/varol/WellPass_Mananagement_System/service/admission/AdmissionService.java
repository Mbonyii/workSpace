package com.varol.WellPass_Mananagement_System.service.admission;


import com.varol.WellPass_Mananagement_System.dtos.request.admission.AdmitEmployeeRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.admission.OTPVerificationRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.admission.AdmissionResponse;

public interface AdmissionService {
    
    AdmissionResponse initiateAdmission(AdmitEmployeeRequest request);
    
    AdmissionResponse verifyAndAdmit(OTPVerificationRequest request);
    
    AdmissionResponse getAdmissionDetails(Long attendanceId);
}