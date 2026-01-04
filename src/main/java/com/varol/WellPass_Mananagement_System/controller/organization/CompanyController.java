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
import org.springframework.web.bind.annotation.RestController;

import com.varol.WellPass_Mananagement_System.dtos.response.common.ApiResponse;
import com.varol.WellPass_Mananagement_System.model.organization.Company;
import com.varol.WellPass_Mananagement_System.service.organization.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Company>> createCompany(@Valid @RequestBody Company company) {
        Company created = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Company created successfully", created));
    }

    @PutMapping("/{companyId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<ApiResponse<Company>> updateCompany(
            @PathVariable Long companyId,
            @Valid @RequestBody Company company) {
        
        Company updated = companyService.updateCompany(companyId, company);
        return ResponseEntity.ok(ApiResponse.success("Company updated successfully", updated));
    }

    @GetMapping("/{companyId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<Company>> getCompanyById(@PathVariable Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(ApiResponse.success(company));
    }

    @GetMapping("/code/{companyCode}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN', 'HR_MANAGER')")
    public ResponseEntity<ApiResponse<Company>> getCompanyByCode(@PathVariable String companyCode) {
        Company company = companyService.getCompanyByCode(companyCode);
        return ResponseEntity.ok(ApiResponse.success(company));
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<java.util.List<Company>>> getAllActiveCompanies() {
        java.util.List<Company> companies = companyService.getAllActiveCompanies();
        return ResponseEntity.ok(ApiResponse.success(companies));
    }

    @PutMapping("/{companyId}/deactivate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deactivateCompany(@PathVariable Long companyId) {
        companyService.deactivateCompany(companyId);
        return ResponseEntity.ok(ApiResponse.success("Company deactivated successfully", null));
    }

    @PutMapping("/{companyId}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> activateCompany(@PathVariable Long companyId) {
        companyService.activateCompany(companyId);
        return ResponseEntity.ok(ApiResponse.success("Company activated successfully", null));
    }
}