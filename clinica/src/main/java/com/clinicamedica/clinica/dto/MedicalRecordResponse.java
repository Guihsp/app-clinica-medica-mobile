package com.clinicamedica.clinica.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MedicalRecordResponse {
    private Long id;
    private String diagnosis;
    private String medicName;
    private LocalDateTime createdAt;
    private List<PrescriptionDTO> prescriptions;
    private List<ExamRequestDTO> exams;
}
