package com.varol.WellPass_Mananagement_System.controller.organization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.model.organization.ServiceProvider;
import com.varol.WellPass_Mananagement_System.service.organization.ServiceProviderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/service-providers")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService serviceProviderService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<ServiceProvider>> createServiceProvider(
            @Valid @RequestBody ServiceProvider serviceProvider) {
        
        ServiceProvider created = serviceProviderService.createServiceProvider(serviceProvider);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Service provider created successfully", created));
    }

    @PutMapping("/{providerId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SERVICE_PROVIDER_ADMIN')")
    public ResponseEntity<ApiResponse<ServiceProvider>> updateServiceProvider(
            @PathVariable Long providerId,
            @Valid @RequestBody ServiceProvider serviceProvider) {
        
        ServiceProvider updated = serviceProviderService.updateServiceProvider(providerId, serviceProvider);
        return ResponseEntity.ok(ApiResponse.success("Service provider updated successfully", updated));
    }

    @GetMapping("/{providerId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SERVICE_PROVIDER_ADMIN', 'RECEPTIONIST', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<ServiceProvider>> getServiceProviderById(
            @PathVariable Long providerId) {
        
        ServiceProvider provider = serviceProviderService.getServiceProviderById(providerId);
        return ResponseEntity.ok(ApiResponse.success(provider));
    }

    @GetMapping("/code/{providerCode}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'SERVICE_PROVIDER_ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<ServiceProvider>> getServiceProviderByCode(
            @PathVariable String providerCode) {
        
        ServiceProvider provider = serviceProviderService.getServiceProviderByCode(providerCode);
        return ResponseEntity.ok(ApiResponse.success(provider));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<java.util.List<ServiceProvider>>> getAllActiveServiceProviders() {
        java.util.List<ServiceProvider> providers = serviceProviderService.getAllActiveServiceProviders();
        return ResponseEntity.ok(ApiResponse.success(providers));
    }

    @GetMapping("/location")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<ServiceProvider>>> getServiceProvidersByLocation(
            @RequestParam String location) {
        
        java.util.List<ServiceProvider> providers = serviceProviderService.getServiceProvidersByLocation(location);
        return ResponseEntity.ok(ApiResponse.success(providers));
    }

    @PutMapping("/{providerId}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateServiceProvider(@PathVariable Long providerId) {
        serviceProviderService.deactivateServiceProvider(providerId);
        return ResponseEntity.ok(ApiResponse.success("Service provider deactivated successfully", null));
    }

    @PutMapping("/{providerId}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateServiceProvider(@PathVariable Long providerId) {
        serviceProviderService.activateServiceProvider(providerId);
        return ResponseEntity.ok(ApiResponse.success("Service provider activated successfully", null));
    }
}