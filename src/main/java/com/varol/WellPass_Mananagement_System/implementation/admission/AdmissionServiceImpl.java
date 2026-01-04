package com.varol.WellPass_Mananagement_System.implementation.admission;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.request.admission.AdmitEmployeeRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.admission.OTPVerificationRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.admission.AdmissionResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.attendance.Attendance;
import com.varol.WellPass_Mananagement_System.model.attendance.AttendanceStatus;
import com.varol.WellPass_Mananagement_System.model.user.Employee;
import com.varol.WellPass_Mananagement_System.model.user.EmployeeStatus;
import com.varol.WellPass_Mananagement_System.model.verification.OTPStatus;
import com.varol.WellPass_Mananagement_System.model.verification.OTPType;
import com.varol.WellPass_Mananagement_System.model.verification.OTPVerification;
import com.varol.WellPass_Mananagement_System.repository.attendance.AttendanceRepository;
import com.varol.WellPass_Mananagement_System.repository.organization.ServiceProviderRepository;
import com.varol.WellPass_Mananagement_System.repository.organization.ServiceRepository;
import com.varol.WellPass_Mananagement_System.repository.user.EmployeeRepository;
import com.varol.WellPass_Mananagement_System.repository.verification.OTPVerificationRepository;
import com.varol.WellPass_Mananagement_System.service.admission.AdmissionService;
import com.varol.WellPass_Mananagement_System.service.notification.SMSService;
import com.varol.WellPass_Mananagement_System.service.verification.OTPService;
import com.varol.WellPass_Mananagement_System.service.verification.OTPValidationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdmissionServiceImpl implements AdmissionService {

    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final AttendanceRepository attendanceRepository;
    private final OTPService otpService;
    private final OTPValidationService otpValidationService;
    private final OTPVerificationRepository otpVerificationRepository;
    private final SMSService smsService;

    @Override
    @Transactional
    public AdmissionResponse initiateAdmission(AdmitEmployeeRequest request) {
        Employee employee = employeeRepository.findByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + request.getEmployeeId()));

        if (employee.getStatus() != EmployeeStatus.ACTIVE) {
            throw new IllegalStateException("Employee account is not active");
        }

        var service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        var serviceProvider = serviceProviderRepository.findById(request.getServiceProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Service provider not found"));

        otpService.generateAndSendOTP(employee.getId(), OTPType.SMS);

        AdmissionResponse response = new AdmissionResponse();
        response.setEmployeeId(employee.getEmployeeId());
        response.setEmployeeName(employee.getFullName());
        response.setCompanyName(employee.getCompany().getCompanyName());
        response.setDepartment(employee.getDepartment());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setProfilePictureUrl(employee.getProfilePictureUrl());
        response.setServiceName(service.getServiceName());
        response.setServicePrice(service.getPricePerVisit());
        response.setServiceProviderName(serviceProvider.getProviderName());
        response.setOtpSent(true);
        response.setMessage("OTP sent to employee's phone. Please verify to grant access.");

        return response;
    }

    @Override
    @Transactional
    public AdmissionResponse verifyAndAdmit(OTPVerificationRequest request) {
        Employee employee = employeeRepository.findByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        boolean isValid = otpValidationService.validateOTP(employee.getId(), request.getOtpCode());

        if (!isValid) {
            throw new IllegalArgumentException("Invalid or expired OTP");
        }

        List<OTPVerification> validOTPs = otpVerificationRepository.findValidOTPsByEmployee(
            employee.getId(), 
            OTPStatus.PENDING, 
            LocalDateTime.now()
        );

        OTPVerification matchedOTP = validOTPs.stream()
                .filter(otp -> otp.getOtpCode().equals(request.getOtpCode()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("OTP not found"));

        otpValidationService.markOTPAsVerified(matchedOTP.getId());

        var service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        var serviceProvider = serviceProviderRepository.findById(request.getServiceProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Service provider not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCompany(employee.getCompany());
        attendance.setService(service);
        attendance.setServiceProvider(serviceProvider);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.CHECKED_IN);
        attendance.setPrice(service.getPricePerVisit());
        attendance.setVerifiedByOtp(true);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a, MMM dd, yyyy");
        String formattedTime = savedAttendance.getCheckInTime().format(formatter);

        smsService.sendAdmissionConfirmationSMS(
            employee.getPhoneNumber(),
            employee.getFullName(),
            service.getServiceName(),
            formattedTime
        );

        AdmissionResponse response = new AdmissionResponse();
        response.setAttendanceId(savedAttendance.getId());
        response.setEmployeeId(employee.getEmployeeId());
        response.setEmployeeName(employee.getFullName());
        response.setCompanyName(employee.getCompany().getCompanyName());
        response.setDepartment(employee.getDepartment());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setProfilePictureUrl(employee.getProfilePictureUrl());
        response.setServiceName(service.getServiceName());
        response.setServicePrice(service.getPricePerVisit());
        response.setServiceProviderName(serviceProvider.getProviderName());
        response.setCheckInTime(savedAttendance.getCheckInTime());
        response.setOtpSent(false);
        response.setMessage("Access granted successfully. Attendance logged.");

        return response;
    }

    @Override
    public AdmissionResponse getAdmissionDetails(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        AdmissionResponse response = new AdmissionResponse();
        response.setAttendanceId(attendance.getId());
        response.setEmployeeId(attendance.getEmployee().getEmployeeId());
        response.setEmployeeName(attendance.getEmployee().getFullName());
        response.setCompanyName(attendance.getCompany().getCompanyName());
        response.setDepartment(attendance.getEmployee().getDepartment());
        response.setServiceName(attendance.getService().getServiceName());
        response.setServicePrice(attendance.getPrice());
        response.setServiceProviderName(attendance.getServiceProvider().getProviderName());
        response.setCheckInTime(attendance.getCheckInTime());
        response.setOtpSent(false);
        response.setMessage("Admission details retrieved successfully");

        return response;
    }
}