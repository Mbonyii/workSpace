package com.varol.WellPass_Mananagement_System.repository.billing;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.billing.Invoice;
import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByCompanyId(Long companyId);

    Page<Invoice> findByCompanyId(Long companyId, Pageable pageable);

    Optional<Invoice> findByCompanyIdAndYearAndMonth(Long companyId, Integer year, Integer month);

    List<Invoice> findByStatus(InvoiceStatus status);

    List<Invoice> findByCompanyIdAndStatus(Long companyId, InvoiceStatus status);

    Boolean existsByCompanyIdAndYearAndMonth(Long companyId, Integer year, Integer month);

    Boolean existsByInvoiceNumber(String invoiceNumber);
}







