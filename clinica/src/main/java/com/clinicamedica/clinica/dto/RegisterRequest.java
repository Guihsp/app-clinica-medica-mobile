package com.clinicamedica.clinica.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String typeUser; // Deve ser "PACIENTE", "MEDICO" ou "FUNCIONARIO"
}
