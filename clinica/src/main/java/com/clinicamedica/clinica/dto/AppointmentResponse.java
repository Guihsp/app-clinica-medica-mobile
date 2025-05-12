package com.clinicamedica.clinica.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentResponse {
    private Long id;
    private String medicName;
    private LocalDateTime dateTime;
    private String status;
    private String patientName;

}
