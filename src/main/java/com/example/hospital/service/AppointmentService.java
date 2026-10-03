package com.example.hospital.service;

import com.example.hospital.Entity.Appointment;
import com.example.hospital.Entity.Doctor;
import com.example.hospital.Entity.Patient;
import com.example.hospital.dto.AppointmentResponseDto;
import com.example.hospital.dto.CreateAppointmentRequestDto;
import com.example.hospital.repository.AppointmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto, Long userId) {
        Long doctorId = createAppointmentRequestDto.getDoctorId();

        if (doctorId == null || createAppointmentRequestDto.getAppointmentTime() == null
                || createAppointmentRequestDto.getReason() == null || createAppointmentRequestDto.getReason().isBlank())
            throw new IllegalArgumentException("Doctor, appointment time and reason are required");

        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found for user ID: " + userId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Appointment appointment = Appointment.builder()
                .reason(createAppointmentRequestDto.getReason())
                .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
                .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment); // to maintain consistency

        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }
    @Transactional
    public Appointment reAssignToNewDoctor(Long appointmentId, Long doctorId){
        Appointment appointment=appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor);

        doctor.getAppointments().add(appointment);
        return appointment;

    }

    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long userId) {
        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found for user ID: " + userId));

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}

