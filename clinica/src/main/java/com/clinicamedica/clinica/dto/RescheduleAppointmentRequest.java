package com.clinicamedica.clinica.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RescheduleAppointmentRequest {
    private LocalDateTime newDateTime;
}
