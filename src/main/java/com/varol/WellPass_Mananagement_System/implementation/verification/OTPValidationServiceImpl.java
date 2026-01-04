package com.varol.WellPass_Mananagement_System.implementation.verification;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.verification.OTPStatus;
import com.varol.WellPass_Mananagement_System.model.verification.OTPVerification;
import com.varol.WellPass_Mananagement_System.repository.verification.OTPVerificationRepository;
import com.varol.WellPass_Mananagement_System.service.verification.OTPValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OTPValidationServiceImpl implements OTPValidationService {

    private final OTPVerificationRepository otpRepository;

    @Override
    public boolean validateOTP(Long employeeId, String otpCode) {
        List<OTPVerification> validOTPs = otpRepository.findValidOTPsByEmployee(
            employeeId, 
            OTPStatus.PENDING, 
            LocalDateTime.now()
        );

        for (OTPVerification otp : validOTPs) {
            if (otp.getOtpCode().equals(otpCode) && otp.getAttemptCount() < otp.getMaxAttempts()) {
                return true;
            }
        }

        return false;
    }

    @Override
    @Transactional
    public void markOTPAsVerified(Long otpId) {
        OTPVerification otp = otpRepository.findById(otpId)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found"));

        otp.setStatus(OTPStatus.VERIFIED);
        otp.setVerifiedAt(LocalDateTime.now());
        otpRepository.save(otp);
    }

    @Override
    @Transactional
    public void incrementAttemptCount(Long otpId) {
        OTPVerification otp = otpRepository.findById(otpId)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found"));

        otp.setAttemptCount(otp.getAttemptCount() + 1);

        if (otp.getAttemptCount() >= otp.getMaxAttempts()) {
            otp.setStatus(OTPStatus.FAILED);
        }

        otpRepository.save(otp);
    }

    @Override
    public boolean hasExceededMaxAttempts(Long otpId) {
        OTPVerification otp = otpRepository.findById(otpId)
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found"));

        return otp.getAttemptCount() >= otp.getMaxAttempts();
    }
}