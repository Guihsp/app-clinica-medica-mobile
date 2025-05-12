package com.clinicamedica.clinica.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateMedicalRecordRequest {
    private Long appointmentId;
    private String diagnosis;
    private List<PrescriptionDTO> prescriptions;
    private List<ExamRequestDTO> exams;
}
