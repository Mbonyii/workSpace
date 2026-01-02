package com.varol.WellPass_Mananagement_System.repository.user;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.user.Employee;
import com.varol.WellPass_Mananagement_System.model.user.EmployeeStatus;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeId(String employeeId);

    Boolean existsByEmployeeId(String employeeId);

    Page<Employee> findByCompanyId(Long companyId, Pageable pageable);

    Page<Employee> findByCompanyIdAndStatus(Long companyId, EmployeeStatus status, Pageable pageable);

    Page<Employee> findByCompanyIdAndDepartment(Long companyId, String department, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.company.id = :companyId AND " +
           "(LOWER(e.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Employee> searchEmployees(@Param("companyId") Long companyId, @Param("search") String search, Pageable pageable);

    Long countByCompanyId(Long companyId);

    Long countByCompanyIdAndStatus(Long companyId, EmployeeStatus status);
}