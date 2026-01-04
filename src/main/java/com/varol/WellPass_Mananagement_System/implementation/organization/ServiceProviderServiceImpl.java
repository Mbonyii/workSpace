package com.varol.WellPass_Mananagement_System.implementation.organization;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.exception.custom.DuplicateResourceException;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceProvider;
import com.varol.WellPass_Mananagement_System.repository.organization.ServiceProviderRepository;
import com.varol.WellPass_Mananagement_System.service.organization.ServiceProviderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceProviderServiceImpl implements ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    @Transactional
    public ServiceProvider createServiceProvider(ServiceProvider serviceProvider) {
        if (serviceProviderRepository.existsByProviderCode(serviceProvider.getProviderCode())) {
            throw new DuplicateResourceException("Provider code already exists: " + serviceProvider.getProviderCode());
        }

        serviceProvider.setIsActive(true);
        return serviceProviderRepository.save(serviceProvider);
    }

    @Override
    @Transactional
    public ServiceProvider updateServiceProvider(Long providerId, ServiceProvider serviceProvider) {
        ServiceProvider existing = getServiceProviderById(providerId);

        if (serviceProvider.getProviderName() != null) {
            existing.setProviderName(serviceProvider.getProviderName());
        }
        if (serviceProvider.getAddress() != null) {
            existing.setAddress(serviceProvider.getAddress());
        }
        if (serviceProvider.getPhoneNumber() != null) {
            existing.setPhoneNumber(serviceProvider.getPhoneNumber());
        }
        if (serviceProvider.getEmail() != null) {
            existing.setEmail(serviceProvider.getEmail());
        }
        if (serviceProvider.getLogoUrl() != null) {
            existing.setLogoUrl(serviceProvider.getLogoUrl());
        }
        if (serviceProvider.getBranchLocation() != null) {
            existing.setBranchLocation(serviceProvider.getBranchLocation());
        }

        return serviceProviderRepository.save(existing);
    }

    @Override
    public ServiceProvider getServiceProviderById(Long providerId) {
        return serviceProviderRepository.findById(providerId)
                .orElseThrow(() -> new ResourceNotFoundException("Service provider not found"));
    }

    @Override
    public ServiceProvider getServiceProviderByCode(String providerCode) {
        return serviceProviderRepository.findByProviderCode(providerCode)
                .orElseThrow(() -> new ResourceNotFoundException("Service provider not found with code: " + providerCode));
    }

    @Override
    public List<ServiceProvider> getAllActiveServiceProviders() {
        return serviceProviderRepository.findByIsActive(true);
    }

    @Override
    public List<ServiceProvider> getServiceProvidersByLocation(String location) {
        return serviceProviderRepository.findByBranchLocation(location);
    }

    @Override
    @Transactional
    public void deactivateServiceProvider(Long providerId) {
        ServiceProvider provider = getServiceProviderById(providerId);
        provider.setIsActive(false);
        serviceProviderRepository.save(provider);
    }

    @Override
    @Transactional
    public void activateServiceProvider(Long providerId) {
        ServiceProvider provider = getServiceProviderById(providerId);
        provider.setIsActive(true);
        serviceProviderRepository.save(provider);
    }

    @Override
    public boolean existsByProviderCode(String providerCode) {
        return serviceProviderRepository.existsByProviderCode(providerCode);
    }
}