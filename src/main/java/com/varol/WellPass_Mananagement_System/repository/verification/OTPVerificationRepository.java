package com.varol.WellPass_Mananagement_System.repository.verification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.verification.OTPStatus;
import com.varol.WellPass_Mananagement_System.model.verification.OTPVerification;

@Repository
public interface OTPVerificationRepository extends JpaRepository<OTPVerification, Long> {

    Optional<OTPVerification> findByEmployeeIdAndOtpCodeAndStatus(Long employeeId, String otpCode, OTPStatus status);

    List<OTPVerification> findByEmployeeIdAndStatus(Long employeeId, OTPStatus status);

    @Query("SELECT o FROM OTPVerification o WHERE o.employee.id = :employeeId AND " +
           "o.status = :status AND o.expiresAt > :currentTime ORDER BY o.createdAt DESC")
    List<OTPVerification> findValidOTPsByEmployee(@Param("employeeId") Long employeeId,
                                                    @Param("status") OTPStatus status,
                                                    @Param("currentTime") LocalDateTime currentTime);

    @Modifying
    @Query("UPDATE OTPVerification o SET o.status = :newStatus WHERE o.expiresAt < :currentTime AND o.status = :oldStatus")
    void expireOldOTPs(@Param("currentTime") LocalDateTime currentTime,
                       @Param("oldStatus") OTPStatus oldStatus,
                       @Param("newStatus") OTPStatus newStatus);

    @Modifying
    @Query("DELETE FROM OTPVerification o WHERE o.expiresAt < :cutoffTime")
    void deleteExpiredOTPs(@Param("cutoffTime") LocalDateTime cutoffTime);
}






