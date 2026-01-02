package com.varol.WellPass_Mananagement_System.service.verification;

public interface OTPValidationService {
    
    boolean validateOTP(Long employeeId, String otpCode);
    
    void markOTPAsVerified(Long otpId);
    
    void incrementAttemptCount(Long otpId);
    
    boolean hasExceededMaxAttempts(Long otpId);
}






