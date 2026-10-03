package com.example.hospital.Controller;

import com.example.hospital.dto.AppointmentResponseDto;
import com.example.hospital.dto.CreateAppointmentRequestDto;
import com.example.hospital.dto.PatientResponseDto;
import com.example.hospital.Entity.User;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto)
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto, user.getId()));
    }

    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(patientService.getPatientByUserId(user.getId()));
    }

}
