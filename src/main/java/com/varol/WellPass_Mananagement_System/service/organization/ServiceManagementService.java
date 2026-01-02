package com.varol.WellPass_Mananagement_System.service.organization;

import java.util.List;

import com.varol.WellPass_Mananagement_System.model.organization.Service;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceType;

public interface ServiceManagementService {
    
    Service createService(Service service);
    
    Service updateService(Long serviceId, Service service);
    
    Service getServiceById(Long serviceId);
    
    List<Service> getServicesByProvider(Long serviceProviderId);
    
    List<Service> getActiveServicesByProvider(Long serviceProviderId);
    
    List<Service> getServicesByType(ServiceType serviceType);
    
    void deactivateService(Long serviceId);
    
    void activateService(Long serviceId);
    
    void deleteService(Long serviceId);
}