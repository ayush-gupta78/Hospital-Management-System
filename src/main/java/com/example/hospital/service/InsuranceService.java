package com.example.hospital.service;

import com.example.hospital.Entity.Insurance;
import com.example.hospital.Entity.Patient;
import com.example.hospital.repository.InsuranceRepository;
import com.example.hospital.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;

    private final PatientRepository patientRepository;

    @Transactional
    public Patient AssignInsuranceToPatient(Insurance insurance , Long PatientId){
        Patient patient;
        patient = patientRepository.findById(PatientId)
                .orElseThrow(() ->new EntityNotFoundException("Patient not found with Id:"+PatientId));

        patient.setInsurance(insurance);

        insurance.setPatient(patient);// this doesn't do shit , just to maintain bidirectional consistency
        return patient;
    }

    @Transactional
    public Patient cancelInsurance(Long patientId){
        Patient patient=patientRepository.findById(patientId).orElseThrow();

        patient.setInsurance(null);
        return patient;

    }
}
