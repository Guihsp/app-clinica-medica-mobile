package com.clinicamedica.clinica.service;

import com.clinicamedica.clinica.dto.LoginRequest;
import com.clinicamedica.clinica.dto.LoginResponse;
import com.clinicamedica.clinica.model.User;
import com.clinicamedica.clinica.repository.UserRepository;
import com.clinicamedica.clinica.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token,
                user.getTypeUser().name()
        );
    }

}
