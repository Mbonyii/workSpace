package com.varol.WellPass_Mananagement_System.service.admission;

import com.varol.WellPass_Mananagement_System.dtos.response.admission.QRCodeResponse;

public interface QRCodeService {
    
    QRCodeResponse generateQRCode(String employeeId);
    
    String readQRCode(byte[] qrCodeImage);
    
    byte[] getQRCodeImage(String employeeId);
}
