package com.clinicamedica.clinica.controller;

import com.clinicamedica.clinica.dto.RegisterPatientRequest;
import com.clinicamedica.clinica.dto.RegisterResponse;
import com.clinicamedica.clinica.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterPatientRequest request) {
        return ResponseEntity.ok(patientService.registerPatient(request));
    }
}
