package com.varol.WellPass_Mananagement_System.implementation.organization;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.varol.WellPass_Mananagement_System.exception.custom.DuplicateResourceException;
import com.varol.WellPass_Mananagement_System.exception.custom.ResourceNotFoundException;
import com.varol.WellPass_Mananagement_System.model.organization.Company;
import com.varol.WellPass_Mananagement_System.repository.organization.CompanyRepository;
import com.varol.WellPass_Mananagement_System.service.organization.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Company createCompany(Company company) {
        if (companyRepository.existsByCompanyCode(company.getCompanyCode())) {
            throw new DuplicateResourceException("Company code already exists: " + company.getCompanyCode());
        }

        if (companyRepository.existsByCompanyName(company.getCompanyName())) {
            throw new DuplicateResourceException("Company name already exists: " + company.getCompanyName());
        }

        company.setIsActive(true);
        return companyRepository.save(company);
    }

    @Override
    @Transactional
    public Company updateCompany(Long companyId, Company company) {
        Company existing = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        if (company.getCompanyName() != null) {
            existing.setCompanyName(company.getCompanyName());
        }
        if (company.getAddress() != null) {
            existing.setAddress(company.getAddress());
        }
        if (company.getPhoneNumber() != null) {
            existing.setPhoneNumber(company.getPhoneNumber());
        }
        if (company.getEmail() != null) {
            existing.setEmail(company.getEmail());
        }
        if (company.getLogoUrl() != null) {
            existing.setLogoUrl(company.getLogoUrl());
        }

        return companyRepository.save(existing);
    }

    @Override
    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    public Company getCompanyByCode(String companyCode) {
        return companyRepository.findByCompanyCode(companyCode)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with code: " + companyCode));
    }

    @Override
    public List<Company> getAllActiveCompanies() {
        return companyRepository.findByIsActive(true);
    }

    @Override
    @Transactional
    public void deactivateCompany(Long companyId) {
        Company company = getCompanyById(companyId);
        company.setIsActive(false);
        companyRepository.save(company);
    }

    @Override
    @Transactional
    public void activateCompany(Long companyId) {
        Company company = getCompanyById(companyId);
        company.setIsActive(true);
        companyRepository.save(company);
    }

    @Override
    public boolean existsByCompanyCode(String companyCode) {
        return companyRepository.existsByCompanyCode(companyCode);
    }
}