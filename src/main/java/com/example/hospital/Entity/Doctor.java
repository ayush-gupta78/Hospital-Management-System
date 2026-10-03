package com.example.hospital.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String specialization;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Appointment> appointments;

    @OneToOne(mappedBy = "HeadDoctor")
    private Department department;

    @ManyToMany(mappedBy = "doctors")
    @ToString.Exclude
    private Set<Department> departments=new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}
