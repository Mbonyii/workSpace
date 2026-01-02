package com.varol.WellPass_Mananagement_System.repository.organization;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.organization.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCompanyCode(String companyCode);

    Optional<Company> findByCompanyName(String companyName);

    Boolean existsByCompanyCode(String companyCode);

    Boolean existsByCompanyName(String companyName);

    List<Company> findByIsActive(Boolean isActive);
}