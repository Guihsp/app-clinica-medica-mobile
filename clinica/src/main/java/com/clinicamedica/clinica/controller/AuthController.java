package com.clinicamedica.clinica.controller;

import com.clinicamedica.clinica.dto.LoginRequest;
import com.clinicamedica.clinica.dto.LoginResponse;
import com.clinicamedica.clinica.security.JwtService;
import com.clinicamedica.clinica.service.AuthService;

import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
