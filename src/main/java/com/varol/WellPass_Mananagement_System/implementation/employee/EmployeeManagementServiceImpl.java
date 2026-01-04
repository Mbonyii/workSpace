package com.varol.WellPass_Mananagement_System.implementation.employee;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.request.employee.BulkEmployeeUploadRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeFilterRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeRegistrationRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeStatsResponse;
import com.varol.WellPass_Mananagement_System.model.user.Employee;
import com.varol.WellPass_Mananagement_System.model.user.EmployeeStatus;
import com.varol.WellPass_Mananagement_System.repository.user.EmployeeRepository;
import com.varol.WellPass_Mananagement_System.service.employee.EmployeeManagementService;
import com.varol.WellPass_Mananagement_System.service.employee.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @Override
    public PageResponse<EmployeeResponse> getEmployeesByCompany(Long companyId, EmployeeFilterRequest filter) {
        Pageable pageable = PageRequest.of(
            filter.getPage(),
            filter.getSize(),
            Sort.Direction.fromString(filter.getSortDirection()),
            filter.getSortBy()
        );

        Page<Employee> employeePage;

        if (filter.getSearchQuery() != null && !filter.getSearchQuery().isEmpty()) {
            employeePage = employeeRepository.searchEmployees(companyId, filter.getSearchQuery(), pageable);
        } else if (filter.getStatus() != null) {
            employeePage = employeeRepository.findByCompanyIdAndStatus(companyId, filter.getStatus(), pageable);
        } else if (filter.getDepartment() != null) {
            employeePage = employeeRepository.findByCompanyIdAndDepartment(companyId, filter.getDepartment(), pageable);
        } else {
            employeePage = employeeRepository.findByCompanyId(companyId, pageable);
        }

        List<EmployeeResponse> content = employeePage.getContent().stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
            content,
            employeePage.getNumber(),
            employeePage.getSize(),
            employeePage.getTotalElements(),
            employeePage.getTotalPages(),
            employeePage.isFirst(),
            employeePage.isLast(),
            employeePage.hasNext(),
            employeePage.hasPrevious()
        );
    }

    @Override
    @Transactional
    public List<EmployeeResponse> bulkUploadEmployees(BulkEmployeeUploadRequest request) {
        List<EmployeeResponse> responses = new ArrayList<>();

        for (EmployeeRegistrationRequest employeeRequest : request.getEmployees()) {
            try {
                EmployeeResponse response = employeeService.registerEmployee(employeeRequest);
                responses.add(response);
            } catch (Exception e) {
                continue;
            }
        }

        return responses;
    }

    @Override
    public EmployeeStatsResponse getCompanyEmployeeStats(Long companyId) {
        Long totalEmployees = employeeRepository.countByCompanyId(companyId);
        Long activeEmployees = employeeRepository.countByCompanyIdAndStatus(companyId, EmployeeStatus.ACTIVE);
        Long inactiveEmployees = employeeRepository.countByCompanyIdAndStatus(companyId, EmployeeStatus.INACTIVE);

        EmployeeStatsResponse stats = new EmployeeStatsResponse();
        stats.setTotalEmployees(totalEmployees);
        stats.setActiveEmployees(activeEmployees);
        stats.setInactiveEmployees(inactiveEmployees);
        stats.setTotalVisitsThisMonth(0);
        stats.setTotalSpentThisMonth(java.math.BigDecimal.ZERO);
        stats.setAverageVisitsPerEmployee(0);

        return stats;
    }

    @Override
    public List<EmployeeResponse> searchEmployees(Long companyId, String query) {
        Pageable pageable = PageRequest.of(0, 100);
        Page<Employee> employees = employeeRepository.searchEmployees(companyId, query, pageable);
        
        return employees.getContent().stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] exportEmployeesToCsv(Long companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId, Pageable.unpaged()).getContent();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        writer.println("Employee ID,Full Name,Email,Phone Number,Department,Status");

        for (Employee employee : employees) {
            writer.printf("%s,%s,%s,%s,%s,%s%n",
                employee.getEmployeeId(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getDepartment(),
                employee.getStatus()
            );
        }

        writer.flush();
        return outputStream.toByteArray();
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
}