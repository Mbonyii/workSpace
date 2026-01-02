package com.varol.WellPass_Mananagement_System.dtos.response.billing;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {

    private Long id;

    private String invoiceNumber;

    private String companyName;

    private Integer year;

    private Integer month;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount;

    private BigDecimal balanceDue;

    private InvoiceStatus status;

    private String pdfUrl;
}







