package com.varol.WellPass_Mananagement_System.controller.organization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.model.organization.Service;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceType;
import com.varol.WellPass_Mananagement_System.service.organization.ServiceManagementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceManagementService serviceManagementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<Service>> createService(@Valid @RequestBody Service service) {
        Service created = serviceManagementService.createService(service);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Service created successfully", created));
    }

    @PutMapping("/{serviceId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<Service>> updateService(
            @PathVariable Long serviceId,
            @Valid @RequestBody Service service) {
        
        Service updated = serviceManagementService.updateService(serviceId, service);
        return ResponseEntity.ok(ApiResponse.success("Service updated successfully", updated));
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<Service>> getServiceById(@PathVariable Long serviceId) {
        Service service = serviceManagementService.getServiceById(serviceId);
        return ResponseEntity.ok(ApiResponse.success(service));
    }

    @GetMapping("/provider/{serviceProviderId}")
    public ResponseEntity<ApiResponse<java.util.List<Service>>> getServicesByProvider(
            @PathVariable Long serviceProviderId) {
        
        java.util.List<Service> services = serviceManagementService.getServicesByProvider(serviceProviderId);
        return ResponseEntity.ok(ApiResponse.success(services));
    }

    @GetMapping("/provider/{serviceProviderId}/active")
    public ResponseEntity<ApiResponse<java.util.List<Service>>> getActiveServicesByProvider(
            @PathVariable Long serviceProviderId) {
        
        java.util.List<Service> services = serviceManagementService.getActiveServicesByProvider(serviceProviderId);
        return ResponseEntity.ok(ApiResponse.success(services));
    }

    @GetMapping("/type")
    public ResponseEntity<ApiResponse<java.util.List<Service>>> getServicesByType(
            @RequestParam ServiceType serviceType) {
        
        java.util.List<Service> services = serviceManagementService.getServicesByType(serviceType);
        return ResponseEntity.ok(ApiResponse.success(services));
    }

    @PutMapping("/{serviceId}/deactivate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateService(@PathVariable Long serviceId) {
        serviceManagementService.deactivateService(serviceId);
        return ResponseEntity.ok(ApiResponse.success("Service deactivated successfully", null));
    }

    @PutMapping("/{serviceId}/activate")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateService(@PathVariable Long serviceId) {
        serviceManagementService.activateService(serviceId);
        return ResponseEntity.ok(ApiResponse.success("Service activated successfully", null));
    }

    @DeleteMapping("/{serviceId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteService(@PathVariable Long serviceId) {
        serviceManagementService.deleteService(serviceId);
        return ResponseEntity.ok(ApiResponse.success("Service deleted successfully", null));
    }
}