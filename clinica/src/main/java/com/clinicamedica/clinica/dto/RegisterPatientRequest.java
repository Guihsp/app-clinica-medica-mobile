package com.clinicamedica.clinica.dto;

import lombok.Data;

@Data
public class RegisterPatientRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String cpf;
    private String address;
}
