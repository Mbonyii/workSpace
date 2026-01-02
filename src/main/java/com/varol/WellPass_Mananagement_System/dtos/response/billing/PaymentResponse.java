package com.varol.WellPass_Mananagement_System.dtos.response.billing;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;

    private String paymentReference;

    private Long invoiceId;

    private String invoiceNumber;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private String paymentMethod;

    private String transactionId;

    private String message;
}






