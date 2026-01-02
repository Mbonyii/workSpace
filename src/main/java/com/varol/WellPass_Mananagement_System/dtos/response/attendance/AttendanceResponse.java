package com.varol.WellPass_Mananagement_System.dtos.response.attendance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.varol.WellPass_Mananagement_System.model.attendance.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {

    private Long id;

    private String employeeId;

    private String employeeName;

    private String profilePictureUrl;

    private String companyName;

    private String department;

    private String serviceName;

    private String serviceProviderName;

    private BigDecimal price;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private AttendanceStatus status;

    private Boolean verifiedByOtp;
}






