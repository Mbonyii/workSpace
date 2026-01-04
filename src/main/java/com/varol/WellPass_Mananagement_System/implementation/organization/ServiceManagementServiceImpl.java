package com.varol.WellPass_Mananagement_System.implementation.organization;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceProvider;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceType;
import com.varol.WellPass_Mananagement_System.repository.organization.ServiceProviderRepository;
import com.varol.WellPass_Mananagement_System.repository.organization.ServiceRepository;
import com.varol.WellPass_Mananagement_System.service.organization.ServiceManagementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceManagementServiceImpl implements ServiceManagementService {

    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    @Override
    @Transactional
    public com.varol.WellPass_Mananagement_System.model.organization.Service createService(
            com.varol.WellPass_Mananagement_System.model.organization.Service service) {
        
        ServiceProvider provider = serviceProviderRepository.findById(service.getServiceProvider().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Service provider not found"));

        service.setServiceProvider(provider);
        service.setIsActive(true);
        return serviceRepository.save(service);
    }

    @Override
    @Transactional
    public com.varol.WellPass_Mananagement_System.model.organization.Service updateService(
            Long serviceId, com.varol.WellPass_Mananagement_System.model.organization.Service service) {
        
        com.varol.WellPass_Mananagement_System.model.organization.Service existing = getServiceById(serviceId);

        if (service.getServiceName() != null) {
            existing.setServiceName(service.getServiceName());
        }
        if (service.getDescription() != null) {
            existing.setDescription(service.getDescription());
        }
        if (service.getPricePerVisit() != null) {
            existing.setPricePerVisit(service.getPricePerVisit());
        }
        if (service.getServiceType() != null) {
            existing.setServiceType(service.getServiceType());
        }

        return serviceRepository.save(existing);
    }

    @Override
    public com.varol.WellPass_Mananagement_System.model.organization.Service getServiceById(Long serviceId) {
        return serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }

    @Override
    public List<com.varol.WellPass_Mananagement_System.model.organization.Service> getServicesByProvider(Long serviceProviderId) {
        return serviceRepository.findByServiceProviderId(serviceProviderId);
    }

    @Override
    public List<com.varol.WellPass_Mananagement_System.model.organization.Service> getActiveServicesByProvider(Long serviceProviderId) {
        return serviceRepository.findByServiceProviderIdAndIsActive(serviceProviderId, true);
    }

    @Override
    public List<com.varol.WellPass_Mananagement_System.model.organization.Service> getServicesByType(ServiceType serviceType) {
        return serviceRepository.findByServiceType(serviceType);
    }

    @Override
    @Transactional
    public void deactivateService(Long serviceId) {
        com.varol.WellPass_Mananagement_System.model.organization.Service service = getServiceById(serviceId);
        service.setIsActive(false);
        serviceRepository.save(service);
    }

    @Override
    @Transactional
    public void activateService(Long serviceId) {
        com.varol.WellPass_Mananagement_System.model.organization.Service service = getServiceById(serviceId);
        service.setIsActive(true);
        serviceRepository.save(service);
    }

    @Override
    @Transactional
    public void deleteService(Long serviceId) {
        if (!serviceRepository.existsById(serviceId)) {
            throw new ResourceNotFoundException("Service not found");
        }
        serviceRepository.deleteById(serviceId);
    }
}