package com.varol.WellPass_Mananagement_System.dtos.response.admission;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionResponse {

    private Long attendanceId;

    private String employeeId;

    private String employeeName;

    private String companyName;

    private String department;

    private String phoneNumber;

    private String profilePictureUrl;

    private String serviceName;

    private BigDecimal servicePrice;

    private String serviceProviderName;

    private LocalDateTime checkInTime;

    private Boolean otpSent;

    private String message;
}






