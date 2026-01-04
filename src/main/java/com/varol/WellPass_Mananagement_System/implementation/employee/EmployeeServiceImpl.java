package com.varol.WellPass_Mananagement_System.implementation.employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeRegistrationRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeUpdateRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeProfileResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.DuplicateResourceException;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.organization.Company;
import com.varol.WellPass_Mananagement_System.model.user.Employee;
import com.varol.WellPass_Mananagement_System.model.user.EmployeeStatus;
import com.varol.WellPass_Mananagement_System.model.user.Role;
import com.varol.WellPass_Mananagement_System.repository.attendance.AttendanceRepository;
import com.varol.WellPass_Mananagement_System.repository.organization.CompanyRepository;
import com.varol.WellPass_Mananagement_System.repository.user.EmployeeRepository;
import com.varol.WellPass_Mananagement_System.service.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public EmployeeResponse registerEmployee(EmployeeRegistrationRequest request) {
        if (employeeRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new DuplicateResourceException("Employee ID already exists: " + request.getEmployeeId());
        }

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Employee employee = new Employee();
        employee.setEmployeeId(request.getEmployeeId());
        employee.setFullName(request.getFullName());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setEmail(request.getEmail());
        employee.setCompany(company);
        employee.setDepartment(request.getDepartment());
        employee.setProfilePictureUrl(request.getProfilePictureUrl());
        employee.setStatus(EmployeeStatus.ACTIVE);
        employee.setRole(Role.EMPLOYEE);
        employee.setPassword("");
        employee.setIsActive(true);

        Employee saved = employeeRepository.save(employee);
        return mapToEmployeeResponse(saved);
    }

    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Long employeeId, EmployeeUpdateRequest request) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (request.getFullName() != null) {
            employee.setFullName(request.getFullName());
        }
        if (request.getPhoneNumber() != null) {
            employee.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }
        if (request.getDepartment() != null) {
            employee.setDepartment(request.getDepartment());
        }
        if (request.getProfilePictureUrl() != null) {
            employee.setProfilePictureUrl(request.getProfilePictureUrl());
        }

        Employee updated = employeeRepository.save(employee);
        return mapToEmployeeResponse(updated);
    }

    @Override
    public EmployeeProfileResponse getEmployeeProfile(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));

        return mapToEmployeeProfileResponse(employee);
    }

    @Override
    public EmployeeProfileResponse getEmployeeProfileById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        return mapToEmployeeProfileResponse(employee);
    }

    @Override
    @Transactional
    public void deactivateEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setStatus(EmployeeStatus.INACTIVE);
        employee.setIsActive(false);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void activateEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setStatus(EmployeeStatus.ACTIVE);
        employee.setIsActive(true);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmployeeId(String employeeId) {
        return employeeRepository.existsByEmployeeId(employeeId);
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setEmployeeId(employee.getEmployeeId());
        response.setFullName(employee.getFullName());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setEmail(employee.getEmail());
        response.setDepartment(employee.getDepartment());
        response.setProfilePictureUrl(employee.getProfilePictureUrl());
        response.setStatus(employee.getStatus());
        response.setCompanyId(employee.getCompany().getId());
        response.setCompanyName(employee.getCompany().getCompanyName());
        response.setCreatedAt(employee.getCreatedAt());
        return response;
    }

    private EmployeeProfileResponse mapToEmployeeProfileResponse(Employee employee) {
        EmployeeProfileResponse response = new EmployeeProfileResponse();
        response.setId(employee.getId());
        response.setEmployeeId(employee.getEmployeeId());
        response.setFullName(employee.getFullName());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setEmail(employee.getEmail());
        response.setDepartment(employee.getDepartment());
        response.setProfilePictureUrl(employee.getProfilePictureUrl());
        response.setStatus(employee.getStatus());
        response.setCompanyName(employee.getCompany().getCompanyName());
        response.setCompanyCode(employee.getCompany().getCompanyCode());

        Integer totalVisits = attendanceRepository.findByEmployeeId(employee.getId()).size();
        response.setTotalVisits(totalVisits);

        Double totalSpent = attendanceRepository.sumPriceByCompanyAndDateRange(
            employee.getCompany().getId(),
            employee.getCreatedAt(),
            java.time.LocalDateTime.now()
        );
        response.setTotalSpent(totalSpent != null ? java.math.BigDecimal.valueOf(totalSpent) : java.math.BigDecimal.ZERO);

        return response;
    }
}