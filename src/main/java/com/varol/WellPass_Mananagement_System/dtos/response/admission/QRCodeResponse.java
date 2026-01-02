package com.varol.WellPass_Mananagement_System.dtos.response.admission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeResponse {

    private String qrCodeData;

    private String qrCodeImageUrl;

    private String employeeId;

    private String employeeName;
}