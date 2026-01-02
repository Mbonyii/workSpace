package com.varol.WellPass_Mananagement_System.service.organization;

import java.util.List;

import com.varol.WellPass_Mananagement_System.model.organization.ServiceProvider;

public interface ServiceProviderService {
    
    ServiceProvider createServiceProvider(ServiceProvider serviceProvider);
    
    ServiceProvider updateServiceProvider(Long providerId, ServiceProvider serviceProvider);
    
    ServiceProvider getServiceProviderById(Long providerId);
    
    ServiceProvider getServiceProviderByCode(String providerCode);
    
    List<ServiceProvider> getAllActiveServiceProviders();
    
    List<ServiceProvider> getServiceProvidersByLocation(String location);
    
    void deactivateServiceProvider(Long providerId);
    
    void activateServiceProvider(Long providerId);
    
    boolean existsByProviderCode(String providerCode);
}
