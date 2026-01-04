package com.varol.WellPass_Mananagement_System.implementation.billing;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.request.billing.PaymentRequest;
import com.varol.WellPass_Mananagement_System.dtos.response.billing.PaymentResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.billing.Invoice;
import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;
import com.varol.WellPass_Mananagement_System.model.billing.Payment;
import com.varol.WellPass_Mananagement_System.repository.billing.InvoiceRepository;
import com.varol.WellPass_Mananagement_System.repository.billing.PaymentRepository;
import com.varol.WellPass_Mananagement_System.service.billing.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        String paymentReference = generatePaymentReference();

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setPaymentReference(paymentReference);
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId(request.getTransactionId());
        payment.setNotes(request.getNotes());

        Payment savedPayment = paymentRepository.save(payment);

        invoice.setPaidAmount(invoice.getPaidAmount().add(request.getAmount()));

        if (invoice.getPaidAmount().compareTo(invoice.getTotalAmount()) >= 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.SENT);
        }

        invoiceRepository.save(invoice);

        PaymentResponse response = new PaymentResponse();
        response.setId(savedPayment.getId());
        response.setPaymentReference(savedPayment.getPaymentReference());
        response.setInvoiceId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setAmount(savedPayment.getAmount());
        response.setPaymentDate(savedPayment.getPaymentDate());
        response.setPaymentMethod(savedPayment.getPaymentMethod());
        response.setTransactionId(savedPayment.getTransactionId());
        response.setMessage("Payment processed successfully");

        return response;
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    @Override
    public Payment getPaymentByReference(String paymentReference) {
        return paymentRepository.findByPaymentReference(paymentReference)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    @Override
    public List<Payment> getPaymentsByInvoice(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }

    @Override
    public String generatePaymentReference() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}