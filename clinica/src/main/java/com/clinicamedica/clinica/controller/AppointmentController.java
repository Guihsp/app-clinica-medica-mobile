package com.clinicamedica.clinica.controller;

import com.clinicamedica.clinica.dto.AppointmentResponse;
import com.clinicamedica.clinica.dto.AvailableTimeSlot;
import com.clinicamedica.clinica.dto.CreateAppointmentRequest;
import com.clinicamedica.clinica.dto.RescheduleAppointmentRequest;
import com.clinicamedica.clinica.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/register")
    public ResponseEntity<Void> create(@RequestBody CreateAppointmentRequest request) {
        appointmentService.createAppointment(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<Void> rescheduleAppointment(
            @PathVariable Long id,
            @RequestBody RescheduleAppointmentRequest request) {
        appointmentService.rescheduleAppointment(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<AppointmentResponse>> getPatientAppointments() {
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

    @GetMapping("/available-slots")
    public ResponseEntity<List<AvailableTimeSlot>> getAvailableSlots(
            @RequestParam Long medicId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.getAvailableTimeSlots(medicId, date));
    }

}
