package com.varol.WellPass_Mananagement_System.model.attendance;

import java.math.BigDecimal;

import com.varol.WellPass_Mananagement_System.model.base.BaseEntity;
import com.varol.WellPass_Mananagement_System.model.organization.Company;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monthly_attendance", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"company_id", "service_provider_id", "month", "year"})
})
public class MonthlyAttendance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "month", nullable = false)
    private Integer month;

    @Column(name = "total_visits", nullable = false)
    private Integer totalVisits = 0;

    @Column(name = "unique_employees", nullable = false)
    private Integer uniqueEmployees = 0;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;
}






