package com.example.hospital.Controller;

import com.example.hospital.dto.LoginRequestDto;
import com.example.hospital.dto.LoginResponseDto;
import com.example.hospital.dto.SignUpRequestDto;
import com.example.hospital.dto.SignupResponseDto;
import com.example.hospital.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signupRequestDto));
    }
}
