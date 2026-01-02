package com.varol.WellPass_Mananagement_System.repository.organization;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.organization.Service;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceType;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByServiceProviderId(Long serviceProviderId);

    List<Service> findByServiceProviderIdAndIsActive(Long serviceProviderId, Boolean isActive);

    List<Service> findByServiceType(ServiceType serviceType);

    List<Service> findByIsActive(Boolean isActive);
}