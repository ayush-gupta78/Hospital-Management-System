package com.example.hospital.service;


import com.example.hospital.Entity.Doctor;
import com.example.hospital.Entity.User;
import com.example.hospital.Entity.type.RoleType;
import com.example.hospital.dto.DoctorResponseDto;
import com.example.hospital.dto.OnboardDoctorRequestDto;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto requestDto) {
        if (requestDto.getEmail() == null || requestDto.getEmail().isBlank()
                || requestDto.getPassword() == null || requestDto.getPassword().isBlank()
                || requestDto.getName() == null || requestDto.getName().isBlank())
            throw new IllegalArgumentException("Email, password and name are required");

        if (userRepository.findByUsername(requestDto.getEmail()).isPresent()
                || doctorRepository.existsByEmail(requestDto.getEmail()))
            throw new IllegalArgumentException("User already exists");

        User user = User.builder()
                .username(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(Set.of(RoleType.DOCTOR))
                .build();
        user = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setName(requestDto.getName());
        doctor.setSpecialization(requestDto.getSpecialization());
        doctor.setEmail(requestDto.getEmail());
        doctor.setUser(user);
        doctor = doctorRepository.save(doctor);

        return modelMapper.map(doctor, DoctorResponseDto.class);
    }
}
