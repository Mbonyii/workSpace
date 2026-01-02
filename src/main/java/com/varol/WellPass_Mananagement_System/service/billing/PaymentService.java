package com.varol.WellPass_Mananagement_System.service.billing;

import java.util.List;

import com.varol.WellPass_Mananagement_System.dtos.request.billing.PaymentRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.billing.PaymentResponse;
import com.varol.WellPass_Mananagement_System.model.billing.Payment;

public interface PaymentService {
    
    PaymentResponse processPayment(PaymentRequest request);
    
    Payment getPaymentById(Long paymentId);
    
    Payment getPaymentByReference(String paymentReference);
    
    List<Payment> getPaymentsByInvoice(Long invoiceId);
    
    String generatePaymentReference();
}
