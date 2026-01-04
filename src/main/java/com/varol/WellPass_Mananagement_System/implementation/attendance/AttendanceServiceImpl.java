package com.varol.WellPass_Mananagement_System.implementation.attendance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.request.attendance.AttendanceFilterRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.attendance.CheckInRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.attendance.CheckOutRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.attendance.AttendanceResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.PageResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.attendance.Attendance;
import com.varol.WellPass_Mananagement_System.model.attendance.AttendanceStatus;
import com.varol.WellPass_Mananagement_System.model.user.Employee;
import com.varol.WellPass_Mananagement_System.repository.attendance.AttendanceRepository;
import com.varol.WellPass_Mananagement_System.repository.organization.ServiceProviderRepository;
import com.varol.WellPass_Mananagement_System.repository.organization.ServiceRepository;
import com.varol.WellPass_Mananagement_System.repository.user.EmployeeRepository;
import com.varol.WellPass_Mananagement_System.service.attendance.AttendanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    @Transactional
    public AttendanceResponse checkIn(CheckInRequest request) {
        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        var service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        var serviceProvider = serviceProviderRepository.findById(request.getServiceProviderId())
                .orElseThrow(() -> new ResourceNotFoundException("Service provider not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setCompany(employee.getCompany());
        attendance.setService(service);
        attendance.setServiceProvider(serviceProvider);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.CHECKED_IN);
        attendance.setPrice(service.getPricePerVisit());
        attendance.setVerifiedByOtp(false);

        Attendance saved = attendanceRepository.save(attendance);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public AttendanceResponse checkOut(CheckOutRequest request) {
        Attendance attendance = attendanceRepository.findById(request.getAttendanceId())
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        attendance.setCheckOutTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.CHECKED_OUT);

        Attendance updated = attendanceRepository.save(attendance);
        return mapToResponse(updated);
    }

    @Override
    public AttendanceResponse getAttendanceById(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found"));

        return mapToResponse(attendance);
    }

    @Override
    public PageResponse<AttendanceResponse> getAttendanceHistory(AttendanceFilterRequest filter) {
        Pageable pageable = PageRequest.of(
            filter.getPage(),
            filter.getSize(),
            Sort.Direction.fromString(filter.getSortDirection()),
            filter.getSortBy()
        );

        Page<Attendance> attendancePage;

        if (filter.getCompanyId() != null && filter.getStartDate() != null && filter.getEndDate() != null) {
            List<Attendance> attendances = attendanceRepository.findByCompanyIdAndDateRange(
                filter.getCompanyId(),
                filter.getStartDate().atStartOfDay(),
                filter.getEndDate().atTime(23, 59, 59)
            );
            attendancePage = new org.springframework.data.domain.PageImpl<>(attendances, pageable, attendances.size());
        } else if (filter.getCompanyId() != null) {
            attendancePage = attendanceRepository.findByCompanyId(filter.getCompanyId(), pageable);
        } else if (filter.getServiceProviderId() != null) {
            attendancePage = attendanceRepository.findByServiceProviderId(filter.getServiceProviderId(), pageable);
        } else if (filter.getEmployeeId() != null) {
            attendancePage = attendanceRepository.findByEmployeeId(filter.getEmployeeId(), pageable);
        } else {
            attendancePage = attendanceRepository.findAll(pageable);
        }

        List<AttendanceResponse> content = attendancePage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
            content,
            attendancePage.getNumber(),
            attendancePage.getSize(),
            attendancePage.getTotalElements(),
            attendancePage.getTotalPages(),
            attendancePage.isFirst(),
            attendancePage.isLast(),
            attendancePage.hasNext(),
            attendancePage.hasPrevious()
        );
    }

    @Override
    public List<AttendanceResponse> getAttendanceByDateRange(Long companyId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Attendance> attendances = attendanceRepository.findByCompanyIdAndDateRange(companyId, startDate, endDate);
        return attendances.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponse> getEmployeeAttendanceHistory(Long employeeId) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId);
        return attendances.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AttendanceResponse mapToResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setId(attendance.getId());
        response.setEmployeeId(attendance.getEmployee().getEmployeeId());
        response.setEmployeeName(attendance.getEmployee().getFullName());
        response.setProfilePictureUrl(attendance.getEmployee().getProfilePictureUrl());
        response.setCompanyName(attendance.getCompany().getCompanyName());
        response.setDepartment(attendance.getEmployee().getDepartment());
        response.setServiceName(attendance.getService().getServiceName());
        response.setServiceProviderName(attendance.getServiceProvider().getProviderName());
        response.setPrice(attendance.getPrice());
        response.setCheckInTime(attendance.getCheckInTime());
        response.setCheckOutTime(attendance.getCheckOutTime());
        response.setStatus(attendance.getStatus());
        response.setVerifiedByOtp(attendance.getVerifiedByOtp());
        return response;
    }
}