package com.varol.WellPass_Mananagement_System.dtos.request.attendance;


import java.time.LocalDate;

import com.varol.WellPass_Mananagement_System.model.attendance.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceFilterRequest {

    private Long companyId;

    private Long serviceProviderId;

    private Long employeeId;

    private AttendanceStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer page = 0;

    private Integer size = 20;

    private String sortBy = "checkInTime";

    private String sortDirection = "DESC";
}






