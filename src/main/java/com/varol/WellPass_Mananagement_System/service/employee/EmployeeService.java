package com.varol.WellPass_Mananagement_System.service.employee;

import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeRegistrationRequest;
import com.varol.WellPass_Mananagement_System.dtos.request.employee.EmployeeUpdateRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeProfileResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.employee.EmployeeResponse;

public interface EmployeeService {
    
    EmployeeResponse registerEmployee(EmployeeRegistrationRequest request);
    
    EmployeeResponse updateEmployee(Long employeeId, EmployeeUpdateRequest request);
    
    EmployeeProfileResponse getEmployeeProfile(String employeeId);
    
    EmployeeProfileResponse getEmployeeProfileById(Long id);
    
    void deactivateEmployee(String employeeId);
    
    void activateEmployee(String employeeId);
    
    void deleteEmployee(Long id);
    
    boolean existsByEmployeeId(String employeeId);
}
