    package com.clinicamedica.clinica.service;
    
    import com.clinicamedica.clinica.dto.AvailableTimeSlot;
    import com.clinicamedica.clinica.dto.CreateAppointmentRequest;
    import com.clinicamedica.clinica.dto.AppointmentResponse;
    import com.clinicamedica.clinica.dto.RescheduleAppointmentRequest;
    import com.clinicamedica.clinica.model.*;
    import com.clinicamedica.clinica.repository.*;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Service;
    
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Set;
    import java.util.stream.Collectors;
    
    @Service
    @RequiredArgsConstructor
    public class AppointmentService {
    
        private final UserRepository userRepository;
        private final PatientRepository patientRepository;
        private final MedicRepository medicRepository;
        private final AppointmentRepository appointmentRepository;
    
        public void createAppointment(CreateAppointmentRequest request) {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    
            Patient patient;
    
            if (user.getTypeUser() == TypeUser.PATIENT) {
                if (request.getPatientId() != null) {
                    throw new RuntimeException("Pacientes só podem agendar para si mesmos.");
                }
    
                patient = patientRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    
            } else if (user.getTypeUser() == TypeUser.EMPLOYEE) {
                if (request.getPatientId() == null) {
                    throw new RuntimeException("Funcionários devem fornecer o ID do paciente.");
                }
    
                patient = patientRepository.findById(request.getPatientId())
                        .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    
            } else {
                throw new RuntimeException("Apenas pacientes e funcionários podem agendar consultas.");
            }
    
            LocalDateTime dateTime = request.getDateTime();
            LocalTime time = dateTime.toLocalTime();
    
            if (time.getMinute() % 30 != 0 || time.getSecond() != 0) {
                throw new RuntimeException("Horário inválido. Só é permitido agendamento em blocos de 30 minutos.");
            }
    
            if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(18, 0))) {
                throw new RuntimeException("Fora do horário de atendimento (08:00 às 18:00).");
            }
    
            Medic medic = medicRepository.findById(request.getMedicId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    
            boolean conflict = appointmentRepository.existsByMedicAndDateTime(medic, dateTime);
            if (conflict) {
                throw new RuntimeException("Este horário já está ocupado para este médico.");
            }
    
            boolean patientConflict = appointmentRepository.existsByPatientAndDateTime(patient, dateTime);
            if (patientConflict) {
                throw new RuntimeException("Este paciente já possui uma consulta marcada neste horário.");
            }
    
            Appointment appointment = Appointment.builder()
                    .patient(patient)
                    .medic(medic)
                    .dateTime(dateTime)
                    .status(AppointmentStatus.SCHEDULED)
                    .build();
    
            appointmentRepository.save(appointment);
        }

        public List<AppointmentResponse> getAppointments() {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(email).orElseThrow();

            if (user.getTypeUser() == TypeUser.EMPLOYEE) {
                return appointmentRepository.findAll().stream()
                        .map(this::toDto)
                        .toList();

            } else if (user.getTypeUser() == TypeUser.MEDIC) {
                Medic medic = medicRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
                return appointmentRepository.findByMedic(medic).stream()
                        .map(this::toDto)
                        .toList();

            } else if (user.getTypeUser() == TypeUser.PATIENT) {
                Patient patient = patientRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
                return appointmentRepository.findByPatient(patient).stream()
                        .map(this::toDto)
                        .toList();
            }

            throw new RuntimeException("Tipo de usuário inválido.");
        }

        private AppointmentResponse toDto(Appointment a) {
            return AppointmentResponse.builder()
                    .id(a.getId())
                    .medicName(a.getMedic().getUser().getName())
                    .patientName(a.getPatient().getUser().getName())
                    .dateTime(a.getDateTime())
                    .status(a.getStatus().name())
                    .build();
        }


        public List<AvailableTimeSlot> getAvailableTimeSlots(Long medicId, LocalDate date) {
            Medic medic = medicRepository.findById(medicId)
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado"));
    
            List<LocalTime> allSlots = new ArrayList<>();
            LocalTime start = LocalTime.of(8, 0);
            LocalTime end = LocalTime.of(18, 0);
    
            while (!start.isAfter(end)) {
                allSlots.add(start);
                start = start.plusMinutes(30);
            }
    
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59);
            List<Appointment> appointments = appointmentRepository
                    .findByMedicAndDateTimeBetween(medic, startOfDay, endOfDay);
    
            Set<LocalTime> occupied = appointments.stream()
                    .map(a -> a.getDateTime().toLocalTime())
                    .collect(Collectors.toSet());
    
            return allSlots.stream()
                    .filter(slot -> !occupied.contains(slot))
                    .map(slot -> new AvailableTimeSlot(slot.toString()))
                    .collect(Collectors.toList());
        }
    
        public void rescheduleAppointment(Long appointmentId, RescheduleAppointmentRequest request) {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));
    
            LocalDateTime newDateTime = request.getNewDateTime();
            LocalTime time = newDateTime.toLocalTime();
    
            if (time.getMinute() % 30 != 0 || time.getSecond() != 0) {
                throw new RuntimeException("Horário inválido. Use blocos de 30 minutos.");
            }
    
            if (time.isBefore(LocalTime.of(8, 0)) || time.isAfter(LocalTime.of(18, 0))) {
                throw new RuntimeException("Fora do horário permitido (08:00 - 18:00).");
            }
    
            Medic medic = appointment.getMedic();
            Patient patient = appointment.getPatient();
    
            boolean medicConflict = appointmentRepository.existsByMedicAndDateTime(medic, newDateTime);
            if (medicConflict && !appointment.getDateTime().equals(newDateTime)) {
                throw new RuntimeException("Este médico já tem uma consulta neste horário.");
            }
    
            boolean patientConflict = appointmentRepository.existsByPatientAndDateTime(patient, newDateTime);
            if (patientConflict && !appointment.getDateTime().equals(newDateTime)) {
                throw new RuntimeException("Este paciente já possui uma consulta neste horário.");
            }
    
            appointment.setDateTime(newDateTime);
            appointment.setStatus(AppointmentStatus.RESCHEDULED);
            appointmentRepository.save(appointment);
        }
    
        public void cancelAppointment(Long appointmentId) {
            Appointment appointment = appointmentRepository.findById(appointmentId)
                    .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));
    
            appointment.setStatus(AppointmentStatus.CANCELED);
            appointmentRepository.save(appointment);
        }
    
    
    
    }
