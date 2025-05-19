package com.clinicamedica.clinica.config;

import com.clinicamedica.clinica.model.TypeUser;
import com.clinicamedica.clinica.model.User;
import com.clinicamedica.clinica.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String email = "admin@funcionario.com";

        if (userRepository.findByEmail(email).isEmpty()) {
            User user = User.builder()
                    .name("Admin Funcionario")
                    .email(email)
                    .password(passwordEncoder.encode("123456"))
                    .typeUser(TypeUser.EMPLOYEE)
                    .build();

            userRepository.save(user);
            System.out.println("Funcionário admin criado com sucesso!");
        }
    }
}
