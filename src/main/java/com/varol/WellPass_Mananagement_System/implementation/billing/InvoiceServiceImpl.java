package com.varol.WellPass_Mananagement_System.implementation.billing;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.response.billing.InvoiceDetailResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.billing.InvoiceResponse;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.billing.Invoice;
import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;
import com.varol.WellPass_Mananagement_System.repository.billing.InvoiceRepository;
import com.varol.WellPass_Mananagement_System.service.billing.InvoiceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public Invoice getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }

    @Override
    public Invoice getInvoiceByNumber(String invoiceNumber) {
        return invoiceRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found: " + invoiceNumber));
    }

    @Override
    public List<InvoiceResponse> getInvoicesByCompany(Long companyId) {
        List<Invoice> invoices = invoiceRepository.findByCompanyId(companyId);
        return invoices.stream()
                .map(this::mapToInvoiceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDetailResponse getInvoiceDetails(Long invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);

        InvoiceDetailResponse response = new InvoiceDetailResponse();
        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setCompanyName(invoice.getCompany().getCompanyName());
        response.setCompanyAddress(invoice.getCompany().getAddress());
        response.setYear(invoice.getYear());
        response.setMonth(invoice.getMonth());
        response.setIssueDate(invoice.getIssueDate());
        response.setDueDate(invoice.getDueDate());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setPaidAmount(invoice.getPaidAmount());
        response.setBalanceDue(invoice.getTotalAmount().subtract(invoice.getPaidAmount()));
        response.setStatus(invoice.getStatus());
        response.setPdfUrl(invoice.getPdfUrl());

        return response;
    }

    @Override
    @Transactional
    public Invoice updateInvoiceStatus(Long invoiceId, InvoiceStatus status) {
        Invoice invoice = getInvoiceById(invoiceId);
        invoice.setStatus(status);
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> getInvoicesByStatus(InvoiceStatus status) {
        return invoiceRepository.findByStatus(status);
    }

    @Override
    public byte[] downloadInvoicePdf(Long invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);
        return new byte[0];
    }

    private InvoiceResponse mapToInvoiceResponse(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setCompanyName(invoice.getCompany().getCompanyName());
        response.setYear(invoice.getYear());
        response.setMonth(invoice.getMonth());
        response.setIssueDate(invoice.getIssueDate());
        response.setDueDate(invoice.getDueDate());
        response.setTotalAmount(invoice.getTotalAmount());
        response.setPaidAmount(invoice.getPaidAmount());
        response.setBalanceDue(invoice.getTotalAmount().subtract(invoice.getPaidAmount()));
        response.setStatus(invoice.getStatus());
        response.setPdfUrl(invoice.getPdfUrl());
        return response;
    }
}