package com.example.hospital.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentRequestDto {
    private Long doctorId;
    private LocalDateTime appointmentTime;
    private String reason;
}
