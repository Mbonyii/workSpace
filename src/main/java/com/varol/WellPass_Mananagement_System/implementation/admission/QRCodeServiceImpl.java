package com.varol.WellPass_Mananagement_System.implementation.admission;

import org.springframework.stereotype.Service;

import com.varol.WellPass_Mananagement_System.dtos.response.admission.QRCodeResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.user.Employee;
import com.varol.WellPass_Mananagement_System.repository.user.EmployeeRepository;
import com.varol.WellPass_Mananagement_System.service.admission.QRCodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QRCodeServiceImpl implements QRCodeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public QRCodeResponse generateQRCode(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        String qrCodeData = String.format("EMPLOYEE:%s|COMPANY:%s", 
            employee.getEmployeeId(), 
            employee.getCompany().getCompanyCode()
        );

        QRCodeResponse response = new QRCodeResponse();
        response.setQrCodeData(qrCodeData);
        response.setQrCodeImageUrl("/api/qr-codes/" + employeeId + ".png");
        response.setEmployeeId(employee.getEmployeeId());
        response.setEmployeeName(employee.getFullName());

        return response;
    }

    @Override
    public String readQRCode(byte[] qrCodeImage) {
        return "EMPLOYEE:TC-2847|COMPANY:TECHCORP";
    }

    @Override
    public byte[] getQRCodeImage(String employeeId) {
        return new byte[0];
    }
}