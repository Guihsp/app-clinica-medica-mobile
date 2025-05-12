package com.clinicamedica.clinica.repository;

import com.clinicamedica.clinica.model.Patient;
import com.clinicamedica.clinica.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);
}
