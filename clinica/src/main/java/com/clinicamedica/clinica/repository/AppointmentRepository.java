package com.clinicamedica.clinica.repository;

import com.clinicamedica.clinica.model.Appointment;
import com.clinicamedica.clinica.model.Medic;
import com.clinicamedica.clinica.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByMedic(Medic medic);

    boolean existsByMedicAndDateTime(Medic medic, LocalDateTime dateTime);

    List<Appointment> findByMedicAndDateTimeBetween(Medic medic, LocalDateTime start, LocalDateTime end);

    boolean existsByPatientAndDateTime(Patient patient, LocalDateTime dateTime);


}
