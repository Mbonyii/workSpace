package com.varol.WellPass_Mananagement_System.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.varol.WellPass_Mananagement_System.model.user.Receptionist;

@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, Long> {

    List<Receptionist> findByServiceProviderId(Long serviceProviderId);

    Optional<Receptionist> findByEmail(String email);

    Boolean existsByServiceProviderIdAndEmail(Long serviceProviderId, String email);
}






