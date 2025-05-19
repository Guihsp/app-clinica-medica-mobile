package com.clinicamedica.clinica.service;

import com.clinicamedica.clinica.dto.CreateMedicalRecordRequest;
import com.clinicamedica.clinica.dto.ExamRequestDTO;
import com.clinicamedica.clinica.dto.MedicalRecordResponse;
import com.clinicamedica.clinica.dto.PrescriptionDTO;
import com.clinicamedica.clinica.model.*;
import com.clinicamedica.clinica.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final UserRepository userRepository;
    private final MedicRepository medicRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    private final PatientRepository patientRepository;
    public void createMedicalRecord(CreateMedicalRecordRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (user.getTypeUser() != TypeUser.MEDIC) {
            throw new RuntimeException("Apenas médicos podem registrar atendimentos.");
        }

        Medic medic = medicRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));

        if (!appointment.getMedic().getId().equals(medic.getId())) {
            throw new RuntimeException("Este médico não está autorizado a atender esta consulta.");
        }

        List<Prescription> prescriptions = request.getPrescriptions().stream()
                .map(p -> Prescription.builder()
                        .medication(p.getMedication())
                        .dosage(p.getDosage())
                        .instructions(p.getInstructions())
                        .build())
                .collect(Collectors.toList());

        List<ExamRequest> exams = request.getExams().stream()
                .map(e -> ExamRequest.builder()
                        .examType(e.getExamType())
                        .observations(e.getObservations())
                        .build())
                .collect(Collectors.toList());

        MedicalRecord record = MedicalRecord.builder()
                .appointment(appointment)
                .diagnosis(request.getDiagnosis())
                .createdAt(LocalDateTime.now())
                .medic(medic)
                .prescriptions(prescriptions)
                .exams(exams)
                .build();

        prescriptions.forEach(p -> p.setMedicalRecord(record));
        exams.forEach(e -> e.setMedicalRecord(record));

        medicalRecordRepository.save(record);

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

    }

    public MedicalRecordResponse getMedicalRecordById(Long id) {
        MedicalRecord record = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado."));

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        TypeUser type = user.getTypeUser();

        boolean isMedic = type == TypeUser.MEDIC &&
                record.getMedic().getUser().getId().equals(user.getId());

        boolean isPatient = type == TypeUser.PATIENT &&
                record.getAppointment().getPatient().getUser().getId().equals(user.getId());

        boolean isEmployee = type == TypeUser.EMPLOYEE;

        if (!(isMedic || isPatient || isEmployee)) {
            throw new RuntimeException("Acesso negado. Você não tem permissão para visualizar este prontuário.");
        }

        return MedicalRecordResponse.builder()
                .id(record.getId())
                .diagnosis(record.getDiagnosis())
                .medicName(record.getMedic().getUser().getName())
                .createdAt(record.getCreatedAt())
                .prescriptions(record.getPrescriptions().stream()
                        .map(p -> {
                            PrescriptionDTO dto = new PrescriptionDTO();
                            dto.setMedication(p.getMedication());
                            dto.setDosage(p.getDosage());
                            dto.setInstructions(p.getInstructions());
                            return dto;
                        }).toList())
                .exams(record.getExams().stream()
                        .map(e -> {
                            ExamRequestDTO dto = new ExamRequestDTO();
                            dto.setExamType(e.getExamType());
                            dto.setObservations(e.getObservations());
                            return dto;
                        }).toList())
                .build();
    }

    public List<MedicalRecordResponse> getMedicalRecordsByPatientId(Long patientId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        TypeUser type = user.getTypeUser();

        if (type == TypeUser.PATIENT) {

            Patient patient = patientRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

            if (!patient.getId().equals(patientId)) {
                throw new RuntimeException("Acesso negado. Você só pode visualizar seus próprios prontuários.");
            }
        }


        List<MedicalRecord> records = medicalRecordRepository.findByAppointment_Patient_Id(patientId);

        return records.stream().map(record -> MedicalRecordResponse.builder()
                .id(record.getId())
                .diagnosis(record.getDiagnosis())
                .medicName(record.getMedic().getUser().getName())
                .createdAt(record.getCreatedAt())
                .prescriptions(record.getPrescriptions().stream()
                        .map(p -> {
                            PrescriptionDTO dto = new PrescriptionDTO();
                            dto.setMedication(p.getMedication());
                            dto.setDosage(p.getDosage());
                            dto.setInstructions(p.getInstructions());
                            return dto;
                        }).toList())
                .exams(record.getExams().stream()
                        .map(e -> {
                            ExamRequestDTO dto = new ExamRequestDTO();
                            dto.setExamType(e.getExamType());
                            dto.setObservations(e.getObservations());
                            return dto;
                        }).toList())
                .build()
        ).toList();
    }


}
