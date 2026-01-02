package com.varol.WellPass_Mananagement_System.repository.organization;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.organization.CompanySettings;

@Repository
public interface CompanySettingsRepository extends JpaRepository<CompanySettings, Long> {

    Optional<CompanySettings> findByCompanyId(Long companyId);

    Boolean existsByCompanyId(Long companyId);
}