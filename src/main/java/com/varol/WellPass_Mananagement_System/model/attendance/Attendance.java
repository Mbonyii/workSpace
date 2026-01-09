package com.varol.WellPass_Mananagement_System.model.attendance;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.varol.WellPass_Mananagement_System.model.base.BaseEntity;
import com.varol.WellPass_Mananagement_System.model.organization.Company;
import com.varol.WellPass_Mananagement_System.model.organization.Service;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceProvider;
import com.varol.WellPass_Mananagement_System.model.user.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance", indexes = {
        @Index(name = "idx_attendance_employee_id", columnList = "employee_id"),
        @Index(name = "idx_attendance_company_id", columnList = "company_id"),
        @Index(name = "idx_attendance_service_provider_id", columnList = "service_provider_id"),
        @Index(name = "idx_attendance_check_in_time", columnList = "check_in_time")
})
public class Attendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status = AttendanceStatus.CHECKED_IN;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "verified_by_otp", nullable = false)
    private Boolean verifiedByOtp = false;
}






