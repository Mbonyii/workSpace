package com.varol.WellPass_Mananagement_System.model.organization;

import java.util.ArrayList;
import java.util.List;

import com.varol.WellPass_Mananagement_System.model.base.AuditableEntity;
import com.varol.WellPass_Mananagement_System.model.user.Receptionist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "service_providers")
public class ServiceProvider extends AuditableEntity {

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @Column(name = "provider_code", nullable = false, unique = true)
    private String providerCode;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "branch_location")
    private String branchLocation;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Receptionist> receptionists = new ArrayList<>();
}






