package com.varol.WellPass_Mananagement_System.model.verification;

import java.time.LocalDateTime;

import com.varol.WellPass_Mananagement_System.model.base.BaseEntity;
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
@Table(name = "otp_verification", indexes = {
        @Index(name = "idx_otp_employee_id", columnList = "employee_id"),
        @Index(name = "idx_otp_code", columnList = "otp_code"),
        @Index(name = "idx_otp_expires_at", columnList = "expires_at")
})
public class OTPVerification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "otp_code", nullable = false)
    private String otpCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "otp_type", nullable = false)
    private OTPType otpType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OTPStatus status = OTPStatus.PENDING;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "attempt_count", nullable = false)
    private Integer attemptCount = 0;

    @Column(name = "max_attempts", nullable = false)
    private Integer maxAttempts = 3;
}






