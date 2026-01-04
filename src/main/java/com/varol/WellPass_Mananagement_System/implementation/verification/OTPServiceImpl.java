package com.varol.WellPass_Mananagement_System.implementation.verification;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.response.admission.OTPResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.user.Employee;
import com.varol.WellPass_Mananagement_System.model.verification.OTPStatus;
import com.varol.WellPass_Mananagement_System.model.verification.OTPType;
import com.varol.WellPass_Mananagement_System.model.verification.OTPVerification;
import com.varol.WellPass_Mananagement_System.repository.user.EmployeeRepository;
import com.varol.WellPass_Mananagement_System.repository.verification.OTPVerificationRepository;
import com.varol.WellPass_Mananagement_System.service.notification.SMSService;
import com.varol.WellPass_Mananagement_System.service.verification.OTPService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {

    private final OTPVerificationRepository otpRepository;
    private final EmployeeRepository employeeRepository;
    private final SMSService smsService;

    @Override
    @Transactional
    public OTPResponse generateAndSendOTP(Long employeeId, OTPType otpType) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        String otpCode = generateOTPCode();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(3);

        OTPVerification otp = new OTPVerification();
        otp.setEmployee(employee);
        otp.setOtpCode(otpCode);
        otp.setOtpType(otpType);
        otp.setStatus(OTPStatus.PENDING);
        otp.setExpiresAt(expiresAt);
        otp.setAttemptCount(0);
        otp.setMaxAttempts(3);

        otpRepository.save(otp);

        if (otpType == OTPType.SMS) {
            smsService.sendOTPSMS(employee.getPhoneNumber(), otpCode);
        }

        OTPResponse response = new OTPResponse();
        response.setOtpSent(true);
        response.setMessage("OTP sent successfully");
        response.setExpiresAt(expiresAt);
        response.setAttemptsRemaining(3);

        return response;
    }

    @Override
    @Transactional
    public OTPResponse resendOTP(Long employeeId) {
        return generateAndSendOTP(employeeId, OTPType.SMS);
    }

    @Override
    @Transactional
    public void expireOTP(Long otpId) {
        OTPVerification otp = otpRepository.findById(otpId)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found"));

        otp.setStatus(OTPStatus.EXPIRED);
        otpRepository.save(otp);
    }

    @Override
    @Transactional
    public void cleanupExpiredOTPs() {
        otpRepository.expireOldOTPs(LocalDateTime.now(), OTPStatus.PENDING, OTPStatus.EXPIRED);
        otpRepository.deleteExpiredOTPs(LocalDateTime.now().minusDays(7));
    }

    private String generateOTPCode() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}