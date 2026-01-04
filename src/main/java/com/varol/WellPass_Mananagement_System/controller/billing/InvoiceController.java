package com.varol.WellPass_Mananagement_System.controller.billing;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.request.billing.InvoiceGenerationRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.billing.InvoiceDetailResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.billing.InvoiceResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.model.billing.Invoice;
import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;
import com.varol.WellPass_Mananagement_System.service.billing.InvoiceGenerationService;
import com.varol.WellPass_Mananagement_System.service.billing.InvoiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceGenerationService invoiceGenerationService;

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<Invoice>> generateMonthlyInvoice(
            @Valid @RequestBody InvoiceGenerationRequest request) {
        
        Invoice invoice = invoiceGenerationService.generateMonthlyInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Invoice generated successfully", invoice));
    }

    @PostMapping("/generate-all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> generateAllMonthlyInvoices(
            @RequestParam Integer year,
            @RequestParam Integer month) {
        
        invoiceGenerationService.generateAllMonthlyInvoices(year, month);
        return ResponseEntity.ok(ApiResponse.success("All invoices generated successfully", null));
    }

    @GetMapping("/{invoiceId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<InvoiceDetailResponse>> getInvoiceDetails(
            @PathVariable Long invoiceId) {
        
        InvoiceDetailResponse response = invoiceService.getInvoiceDetails(invoiceId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/number/{invoiceNumber}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<Invoice>> getInvoiceByNumber(@PathVariable String invoiceNumber) {
        Invoice invoice = invoiceService.getInvoiceByNumber(invoiceNumber);
        return ResponseEntity.ok(ApiResponse.success(invoice));
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<InvoiceResponse>>> getInvoicesByCompany(
            @PathVariable Long companyId) {
        
        java.util.List<InvoiceResponse> invoices = invoiceService.getInvoicesByCompany(companyId);
        return ResponseEntity.ok(ApiResponse.success(invoices));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<java.util.List<Invoice>>> getInvoicesByStatus(
            @PathVariable InvoiceStatus status) {
        
        java.util.List<Invoice> invoices = invoiceService.getInvoicesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(invoices));
    }

    @PutMapping("/{invoiceId}/status")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<Invoice>> updateInvoiceStatus(
            @PathVariable Long invoiceId,
            @RequestParam InvoiceStatus status) {
        
        Invoice invoice = invoiceService.updateInvoiceStatus(invoiceId, status);
        return ResponseEntity.ok(ApiResponse.success("Invoice status updated successfully", invoice));
    }

    @GetMapping("/{invoiceId}/download")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long invoiceId) {
        byte[] pdfData = invoiceService.downloadInvoicePdf(invoiceId);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfData);
    }
}