package com.varol.WellPass_Mananagement_System.repository.organization;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.organization.ServiceProvider;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    Optional<ServiceProvider> findByProviderCode(String providerCode);

    Boolean existsByProviderCode(String providerCode);

    Boolean existsByProviderName(String providerName);

    List<ServiceProvider> findByIsActive(Boolean isActive);

    List<ServiceProvider> findByBranchLocation(String branchLocation);
}







