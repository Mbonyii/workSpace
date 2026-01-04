package com.varol.WellPass_Mananagement_System.controller.admission;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.varol.WellPass_Mananagement_System.dtos.response.admission.QRCodeResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.service.admission.QRCodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/qr-codes")
@RequiredArgsConstructor
public class QRCodeController {

    private final QRCodeService qrCodeService;

    @PostMapping("/generate/{employeeId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE')")
    public ResponseEntity<ApiResponse<QRCodeResponse>> generateQRCode(@PathVariable String employeeId) {
        QRCodeResponse response = qrCodeService.generateQRCode(employeeId);
        return ResponseEntity.ok(ApiResponse.success("QR code generated successfully", response));
    }

    @GetMapping("/{employeeId}/image")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'HR_MANAGER', 'EMPLOYEE', 'RECEPTIONIST')")
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable String employeeId) {
        byte[] qrCodeImage = qrCodeService.getQRCodeImage(employeeId);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=qrcode.png")
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeImage);
    }

    @PostMapping("/read")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public ResponseEntity<ApiResponse<String>> readQRCode(@RequestParam("file") MultipartFile file) {
        try {
            byte[] qrCodeImage = file.getBytes();
            String qrCodeData = qrCodeService.readQRCode(qrCodeImage);
            return ResponseEntity.ok(ApiResponse.success("QR code read successfully", qrCodeData));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to read QR code"));
        }
    }
}