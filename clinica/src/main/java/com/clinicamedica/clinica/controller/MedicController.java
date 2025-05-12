package com.clinicamedica.clinica.controller;

import com.clinicamedica.clinica.dto.MedicSelectDTO;
import com.clinicamedica.clinica.dto.RegisterMedicRequest;
import com.clinicamedica.clinica.dto.RegisterResponse;
import com.clinicamedica.clinica.service.MedicService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medic")
@RequiredArgsConstructor
public class MedicController {
    private final MedicService medicService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterMedicRequest request) {
        return ResponseEntity.ok(medicService.registerMedic(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<MedicSelectDTO>> listAllMedics() {
        return ResponseEntity.ok(medicService.getAllMedics());
    }

}
