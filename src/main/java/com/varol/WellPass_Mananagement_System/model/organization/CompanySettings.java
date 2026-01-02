package com.varol.WellPass_Mananagement_System.model.organization;

import com.varol.WellPass_Mananagement_System.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_settings")
public class CompanySettings extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, unique = true)
    private Company company;

    @Column(name = "auto_invoice_generation", nullable = false)
    private Boolean autoInvoiceGeneration = true;

    @Column(name = "invoice_day_of_month", nullable = false)
    private Integer invoiceDayOfMonth = 1;

    @Column(name = "enable_email_notifications", nullable = false)
    private Boolean enableEmailNotifications = true;

    @Column(name = "enable_sms_notifications", nullable = false)
    private Boolean enableSmsNotifications = true;

    @Column(name = "max_employees")
    private Integer maxEmployees;

    @Column(name = "monthly_budget")
    private Double monthlyBudget;
}







