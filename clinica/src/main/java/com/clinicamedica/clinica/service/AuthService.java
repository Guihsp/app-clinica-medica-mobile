package com.clinicamedica.clinica.service;

import com.clinicamedica.clinica.dto.LoginRequest;
import com.clinicamedica.clinica.dto.LoginResponse;
import com.clinicamedica.clinica.dto.RegisterRequest;
import com.clinicamedica.clinica.model.User;
import com.clinicamedica.clinica.model.TypeUser;
import com.clinicamedica.clinica.repository.UserRepository;
import com.clinicamedica.clinica.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(token, user.getTypeUser().name());
    }

    public void register(RegisterRequest request) {
        // Pegando o usuário logado
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // Verificando se é FUNCIONARIO
        if (currentUser.getTypeUser() != TypeUser.FUNCIONARIO) {
            throw new RuntimeException("Apenas Funcionários podem registrar novos usuários.");
        }

        // Criar novo usuário
        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .typeUser(TypeUser.valueOf(request.getTypeUser()))
                .build();

        userRepository.save(newUser);
    }
}
