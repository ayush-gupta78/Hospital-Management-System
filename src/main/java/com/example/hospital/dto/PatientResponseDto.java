package com.example.hospital.dto;


import com.example.hospital.Entity.type.BloodGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroup bloodGroup;
}