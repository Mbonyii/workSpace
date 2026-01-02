package com.varol.WellPass_Mananagement_System.service.billing;

import com.varol.WellPass_Mananagement_System.dtos.request.billing.InvoiceGenerationRequest;
import com.varol.WellPass_Mananagement_System.model.billing.Invoice;

public interface InvoiceGenerationService {
    
    Invoice generateMonthlyInvoice(InvoiceGenerationRequest request);
    
    void generateAllMonthlyInvoices(Integer year, Integer month);
    
    String generateInvoiceNumber(Long companyId, Integer year, Integer month);
    
    void generateInvoicePdf(Long invoiceId);
}
