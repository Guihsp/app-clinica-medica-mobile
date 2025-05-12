package com.clinicamedica.clinica.service;

import com.clinicamedica.clinica.dto.RegisterPatientRequest;
import com.clinicamedica.clinica.dto.RegisterResponse;
import com.clinicamedica.clinica.model.Patient;
import com.clinicamedica.clinica.model.TypeUser;
import com.clinicamedica.clinica.model.User;
import com.clinicamedica.clinica.repository.PatientRepository;
import com.clinicamedica.clinica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse registerPatient(RegisterPatientRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (loggedUser.getTypeUser() != TypeUser.EMPLOYEE) {
            throw new RuntimeException("Apenas funcionários podem cadastrar pacientes");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .typeUser(TypeUser.PATIENT)
                .build();
        userRepository.save(user);

        Patient patient = Patient.builder()
                .user(user)
                .phone(request.getPhone())
                .cpf(request.getCpf())
                .address(request.getAddress())
                .build();
        patientRepository.save(patient);

        String message = "Paciente cadastrado com sucesso!";
        return new RegisterResponse(message);
    }
}
