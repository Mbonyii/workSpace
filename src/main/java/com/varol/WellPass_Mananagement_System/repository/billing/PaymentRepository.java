package com.varol.WellPass_Mananagement_System.repository.billing;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.billing.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentReference(String paymentReference);

    List<Payment> findByInvoiceId(Long invoiceId);

    Boolean existsByPaymentReference(String paymentReference);

    Boolean existsByTransactionId(String transactionId);
}






