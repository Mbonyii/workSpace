package com.varol.WellPass_Mananagement_System.dtos.response.billing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.varol.WellPass_Mananagement_System.model.billing.InvoiceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailResponse {

    private Long id;

    private String invoiceNumber;

    private String companyName;

    private String companyAddress;

    private Integer year;

    private Integer month;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount;

    private BigDecimal balanceDue;

    private InvoiceStatus status;

    private String pdfUrl;

    private List<InvoiceItemDetail> items;

    private List<PaymentDetail> payments;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InvoiceItemDetail {
        private String serviceProviderName;
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentDetail {
        private String paymentReference;
        private BigDecimal amount;
        private LocalDate paymentDate;
        private String paymentMethod;
    }
}






