package com.clinicamedica.clinica.controller;

import com.clinicamedica.clinica.dto.CreateMedicalRecordRequest;
import com.clinicamedica.clinica.dto.MedicalRecordResponse;
import com.clinicamedica.clinica.service.MedicalRecordService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<Void> createMedicalRecord(@RequestBody CreateMedicalRecordRequest request) {
        medicalRecordService.createMedicalRecord(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponse> getMedicalRecord(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordById(id));
    }


    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordResponse>> getRecordsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordsByPatientId(patientId));
    }

}
