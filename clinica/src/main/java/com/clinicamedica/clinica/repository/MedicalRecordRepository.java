package com.clinicamedica.clinica.repository;

import com.clinicamedica.clinica.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByAppointment_Patient_Id(Long patientId);

}