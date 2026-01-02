package com.varol.WellPass_Mananagement_System.service.organization;

import java.util.List;

import com.varol.WellPass_Mananagement_System.model.organization.Company;

public interface CompanyService {
    
    Company createCompany(Company company);
    
    Company updateCompany(Long companyId, Company company);
    
    Company getCompanyById(Long companyId);
    
    Company getCompanyByCode(String companyCode);
    
    List<Company> getAllActiveCompanies();
    
    void deactivateCompany(Long companyId);
    
    void activateCompany(Long companyId);
    
    boolean existsByCompanyCode(String companyCode);
}






