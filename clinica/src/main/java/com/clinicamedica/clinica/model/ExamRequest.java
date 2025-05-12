package com.clinicamedica.clinica.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examType;

    private String observations;

    @ManyToOne
    private MedicalRecord medicalRecord;
}
