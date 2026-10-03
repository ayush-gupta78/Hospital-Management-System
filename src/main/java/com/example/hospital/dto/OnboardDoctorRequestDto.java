package com.example.hospital.dto;

import lombok.Data;

@Data
public class OnboardDoctorRequestDto {
    private String name;
    private String specialization;
    private String email;
    private String password;
}
