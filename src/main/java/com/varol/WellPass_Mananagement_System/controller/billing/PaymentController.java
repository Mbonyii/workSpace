package com.varol.WellPass_Mananagement_System.controller.billing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.request.billing.PaymentRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.billing.PaymentResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.model.billing.Payment;
import com.varol.WellPass_Mananagement_System.service.billing.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(
            @Valid @RequestBody PaymentRequest request) {
        
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Payment processed successfully", response));
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<Payment>> getPaymentById(@PathVariable Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    @GetMapping("/reference/{paymentReference}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<Payment>> getPaymentByReference(
            @PathVariable String paymentReference) {
        
        Payment payment = paymentService.getPaymentByReference(paymentReference);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }

    @GetMapping("/invoice/{invoiceId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<Payment>>> getPaymentsByInvoice(
            @PathVariable Long invoiceId) {
        
        java.util.List<Payment> payments = paymentService.getPaymentsByInvoice(invoiceId);
        return ResponseEntity.ok(ApiResponse.success(payments));
    }
}