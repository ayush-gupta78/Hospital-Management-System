package com.example.hospital.dto;

import com.example.hospital.Entity.type.BloodGroup;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BloodGroupResponseEntity {

    private BloodGroup bloodGroup;

    private Long count;
}
