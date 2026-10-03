package com.example.hospital.service;

import com.example.hospital.Entity.Patient;
import com.example.hospital.dto.PatientResponseDto;
import com.example.hospital.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PatientResponseDto getPatientById(long patientId){
//        Patient p1=patientRepository.findById(id).orElseThrow();
//        Patient p2=patientRepository.findById(id).orElseThrow();
//        return p1;
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient Not Found with id: "+ patientId ));
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public PatientResponseDto getPatientByUserId(Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found for user ID: " + userId));
        return modelMapper.map(patient, PatientResponseDto.class);
    }


    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }
}
