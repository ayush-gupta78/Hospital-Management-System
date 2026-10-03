package com.example.hospital.service;

import com.example.hospital.Entity.Patient;
import com.example.hospital.Entity.User;
import com.example.hospital.Entity.type.RoleType;
import com.example.hospital.dto.LoginRequestDto;
import com.example.hospital.dto.LoginResponseDto;
import com.example.hospital.dto.SignUpRequestDto;
import com.example.hospital.dto.SignupResponseDto;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        if (loginRequestDto.getUsername() == null || loginRequestDto.getUsername().isBlank()
                || loginRequestDto.getPassword() == null || loginRequestDto.getPassword().isBlank())
            throw new IllegalArgumentException("Username and password are required");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        return new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());
    }

    @Transactional
    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
        if (signupRequestDto.getUsername() == null || signupRequestDto.getUsername().isBlank()
                || signupRequestDto.getPassword() == null || signupRequestDto.getPassword().isBlank()
                || signupRequestDto.getName() == null || signupRequestDto.getName().isBlank())
            throw new IllegalArgumentException("Username, password and name are required");

        if (userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()
                || patientRepository.existsByEmail(signupRequestDto.getUsername()))
            throw new IllegalArgumentException("User already exists");

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .roles(Set.of(RoleType.PATIENT))
                .build();
        user = userRepository.save(user);

        Patient patient = Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(user)
                .build();
        patientRepository.save(patient);

        return new SignupResponseDto(user.getId(), user.getUsername());
    }
}
