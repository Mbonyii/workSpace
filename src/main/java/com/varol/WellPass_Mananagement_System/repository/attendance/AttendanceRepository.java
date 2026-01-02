package com.varol.WellPass_Mananagement_System.repository.attendance;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.attendance.Attendance;
import com.varol.WellPass_Mananagement_System.model.attendance.AttendanceStatus;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployeeId(Long employeeId);

    Page<Attendance> findByEmployeeId(Long employeeId, Pageable pageable);

    List<Attendance> findByCompanyId(Long companyId);

    Page<Attendance> findByCompanyId(Long companyId, Pageable pageable);

    List<Attendance> findByServiceProviderId(Long serviceProviderId);

    Page<Attendance> findByServiceProviderId(Long serviceProviderId, Pageable pageable);

    @Query("SELECT a FROM Attendance a WHERE a.company.id = :companyId AND " +
           "a.checkInTime BETWEEN :startDate AND :endDate")
    List<Attendance> findByCompanyIdAndDateRange(@Param("companyId") Long companyId,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM Attendance a WHERE a.serviceProvider.id = :serviceProviderId AND " +
           "a.checkInTime BETWEEN :startDate AND :endDate")
    List<Attendance> findByServiceProviderIdAndDateRange(@Param("serviceProviderId") Long serviceProviderId,
                                                           @Param("startDate") LocalDateTime startDate,
                                                           @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM Attendance a WHERE a.checkInTime BETWEEN :startDate AND :endDate")
    List<Attendance> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

    List<Attendance> findByStatus(AttendanceStatus status);

    @Query("SELECT COUNT(DISTINCT a.employee.id) FROM Attendance a WHERE a.company.id = :companyId AND " +
           "a.checkInTime BETWEEN :startDate AND :endDate")
    Long countUniqueEmployeesByCompanyAndDateRange(@Param("companyId") Long companyId,
                                                     @Param("startDate") LocalDateTime startDate,
                                                     @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(a.price) FROM Attendance a WHERE a.company.id = :companyId AND " +
           "a.checkInTime BETWEEN :startDate AND :endDate")
    Double sumPriceByCompanyAndDateRange(@Param("companyId") Long companyId,
                                          @Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate);
}






