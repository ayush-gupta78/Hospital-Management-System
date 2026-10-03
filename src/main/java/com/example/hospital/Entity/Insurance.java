package com.example.hospital.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String policyNumber;

    private String provider;

    private LocalDate validUntil;

    @CreationTimestamp
    private LocalDate createdAt;

    @OneToOne(mappedBy = "insurance")
    @ToString.Exclude
    private Patient patient;
}
