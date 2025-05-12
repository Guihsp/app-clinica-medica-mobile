package com.clinicamedica.clinica.repository;

import com.clinicamedica.clinica.model.Medic;
import com.clinicamedica.clinica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {

    Optional<Medic> findByUser(User user);
}
