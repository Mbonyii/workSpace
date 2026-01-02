package com.varol.WellPass_Mananagement_System.repository.billing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.billing.InvoiceItem;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

    List<InvoiceItem> findByInvoiceId(Long invoiceId);

    List<InvoiceItem> findByServiceProviderId(Long serviceProviderId);
}
