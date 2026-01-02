package com.varol.WellPass_Mananagement_System.service.notification;

public interface SMSService {
    
    void sendSMS(String phoneNumber, String message);
    
    void sendOTPSMS(String phoneNumber, String otpCode);
    
    void sendAdmissionConfirmationSMS(String phoneNumber, String employeeName, String serviceName, String time);
}
