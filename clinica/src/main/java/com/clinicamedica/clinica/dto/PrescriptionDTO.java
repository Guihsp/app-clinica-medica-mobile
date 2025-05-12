package com.clinicamedica.clinica.dto;

import lombok.Data;

@Data
public class PrescriptionDTO {
    private String medication;
    private String dosage;
    private String instructions;
}
