package com.varol.WellPass_Mananagement_System.service.verification;


import com.varol.WellPass_Mananagement_System.dtos.response.admission.OTPResponse;
import com.varol.WellPass_Mananagement_System.model.verification.OTPType;

public interface OTPService {
    
    OTPResponse generateAndSendOTP(Long employeeId, OTPType otpType);
    
    OTPResponse resendOTP(Long employeeId);
    
    void expireOTP(Long otpId);
    
    void cleanupExpiredOTPs();
}






