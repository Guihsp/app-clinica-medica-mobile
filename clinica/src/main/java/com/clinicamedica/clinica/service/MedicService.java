package com.clinicamedica.clinica.service;


import com.clinicamedica.clinica.dto.MedicSelectDTO;
import com.clinicamedica.clinica.dto.RegisterMedicRequest;
import com.clinicamedica.clinica.dto.RegisterResponse;
import com.clinicamedica.clinica.model.Medic;
import com.clinicamedica.clinica.model.TypeUser;
import com.clinicamedica.clinica.model.User;
import com.clinicamedica.clinica.repository.MedicRepository;
import com.clinicamedica.clinica.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicService {
    private final MedicRepository medicRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterResponse registerMedic(RegisterMedicRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (loggedUser.getTypeUser() != TypeUser.EMPLOYEE) {
            throw new RuntimeException("Apenas funcionários podem cadastrar médicos");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .typeUser(TypeUser.MEDIC)
                .build();
        userRepository.save(user);

        Medic medic = Medic.builder()
                .user(user)
                .crm(request.getCrm())
                .phone(request.getPhone())
                .address(request.getAddress())
                .cpf(request.getCpf())
                .build();
        medicRepository.save(medic);

        String message = "Médico cadastrado com sucesso!";
        return new RegisterResponse(message);
    }

    public List<MedicSelectDTO> getAllMedics() {
        return medicRepository.findAll().stream()
                .map(medic -> new MedicSelectDTO(
                        medic.getId(),
                        medic.getUser().getName()
                ))
                .toList();
    }
}
