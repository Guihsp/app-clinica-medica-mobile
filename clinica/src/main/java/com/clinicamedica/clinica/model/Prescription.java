package com.clinicamedica.clinica.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medication;

    private String dosage;

    private String instructions;

    @ManyToOne
    private MedicalRecord medicalRecord;
}
