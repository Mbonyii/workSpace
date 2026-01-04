package com.varol.WellPass_Mananagement_System.implementation.notification;

import org.springframework.stereotype.Service;

import com.varol.WellPass_Mananagement_System.service.notification.SMSService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SMSServiceImpl implements SMSService {

    @Override
    public void sendSMS(String phoneNumber, String message) {
        log.info("Sending SMS to {}: {}", phoneNumber, message);
    }

    @Override
    public void sendOTPSMS(String phoneNumber, String otpCode) {
        String message = String.format(
            "FitLife Wellness Access\n\nYour verification code: %s\nValid for 3 minutes only.\n\nDo not share this code.",
            otpCode
        );
        sendSMS(phoneNumber, message);
    }

    @Override
    public void sendAdmissionConfirmationSMS(String phoneNumber, String employeeName, String serviceName, String time) {
        String message = String.format(
            "FitLife Wellness\n\n✓ Access Confirmed!\n\nService: %s\nTime: %s\n\nEnjoy your workout!",
            serviceName,
            time
        );
        sendSMS(phoneNumber, message);
    }
}   