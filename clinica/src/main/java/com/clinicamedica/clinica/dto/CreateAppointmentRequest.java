package com.clinicamedica.clinica.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentRequest {
    private Long medicId;
    private Long patientId;
    private LocalDateTime dateTime;
}
