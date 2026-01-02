package com.varol.WellPass_Mananagement_System.service.billing;

import java.util.List;

import com.varol.WellPass_Mananagement_System.dtos.response.billing.InvoiceDetailResponse;
import com.varol.WellPass_Mananagement_System.dtos.response.billing.InvoiceResponse;
import com.varol.WellPass_Mananagement_System.model.billing.Invoice;
import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;

public interface InvoiceService {
    
    Invoice getInvoiceById(Long invoiceId);
    
    Invoice getInvoiceByNumber(String invoiceNumber);
    
    List<InvoiceResponse> getInvoicesByCompany(Long companyId);
    
    InvoiceDetailResponse getInvoiceDetails(Long invoiceId);
    
    Invoice updateInvoiceStatus(Long invoiceId, InvoiceStatus status);
    
    List<Invoice> getInvoicesByStatus(InvoiceStatus status);
    
    byte[] downloadInvoicePdf(Long invoiceId);
}
