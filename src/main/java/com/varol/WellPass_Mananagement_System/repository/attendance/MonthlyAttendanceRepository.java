package com.varol.WellPass_Mananagement_System.repository.attendance;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.attendance.MonthlyAttendance;

@Repository
public interface MonthlyAttendanceRepository extends JpaRepository<MonthlyAttendance, Long> {

    Optional<MonthlyAttendance> findByCompanyIdAndServiceProviderIdAndYearAndMonth(
            Long companyId, Long serviceProviderId, Integer year, Integer month);

    List<MonthlyAttendance> findByCompanyIdAndYear(Long companyId, Integer year);

    List<MonthlyAttendance> findByCompanyIdAndYearAndMonth(Long companyId, Integer year, Integer month);

    List<MonthlyAttendance> findByServiceProviderIdAndYearAndMonth(Long serviceProviderId, Integer year, Integer month);

    Boolean existsByCompanyIdAndServiceProviderIdAndYearAndMonth(
            Long companyId, Long serviceProviderId, Integer year, Integer month);
}
