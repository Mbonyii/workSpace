package com.varol.WellPass_Mananagement_System.implementation.billing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.dtos.request.billing.InvoiceGenerationRequest;
import com.varol.WellPass_Mananagement_System.exception.custom.DuplicateResourceException;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.attendance.Attendance;
import com.varol.WellPass_Mananagement_System.model.billing.Invoice;
import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;
import com.varol.WellPass_Mananagement_System.model.organization.Company;
import com.varol.WellPass_Mananagement_System.repository.attendance.AttendanceRepository;
import com.varol.WellPass_Mananagement_System.repository.billing.InvoiceRepository;
import com.varol.WellPass_Mananagement_System.repository.organization.CompanyRepository;
import com.varol.WellPass_Mananagement_System.service.billing.InvoiceGenerationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceGenerationServiceImpl implements InvoiceGenerationService {

    private final InvoiceRepository invoiceRepository;
    private final CompanyRepository companyRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    @Transactional
    public Invoice generateMonthlyInvoice(InvoiceGenerationRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        if (invoiceRepository.existsByCompanyIdAndYearAndMonth(
                request.getCompanyId(), request.getYear(), request.getMonth())) {
            throw new DuplicateResourceException("Invoice already exists for this period");
        }

        LocalDateTime startOfMonth = LocalDateTime.of(request.getYear(), request.getMonth(), 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

        List<Attendance> attendances = attendanceRepository.findByCompanyIdAndDateRange(
            request.getCompanyId(),
            startOfMonth,
            endOfMonth
        );

        BigDecimal totalAmount = attendances.stream()
                .map(Attendance::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String invoiceNumber = generateInvoiceNumber(request.getCompanyId(), request.getYear(), request.getMonth());

        Invoice invoice = new Invoice();
        invoice.setCompany(company);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setYear(request.getYear());
        invoice.setMonth(request.getMonth());
        invoice.setIssueDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(30));
        invoice.setTotalAmount(totalAmount);
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setStatus(InvoiceStatus.DRAFT);

        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void generateAllMonthlyInvoices(Integer year, Integer month) {
        List<Company> companies = companyRepository.findByIsActive(true);

        for (Company company : companies) {
            try {
                InvoiceGenerationRequest request = new InvoiceGenerationRequest();
                request.setCompanyId(company.getId());
                request.setYear(year);
                request.setMonth(month);
                generateMonthlyInvoice(request);
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Override
    public String generateInvoiceNumber(Long companyId, Integer year, Integer month) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        return String.format("INV-%s-%04d%02d", company.getCompanyCode(), year, month);
    }

    @Override
    public void generateInvoicePdf(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
    }
}