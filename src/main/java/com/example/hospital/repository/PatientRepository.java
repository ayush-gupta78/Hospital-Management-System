package com.example.hospital.repository;

import com.example.hospital.Entity.Patient;
import com.example.hospital.Entity.type.BloodGroup;
import com.example.hospital.dto.BloodGroupResponseEntity;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByUserId(Long userId);
    boolean existsByEmail(String email);

    Patient getPatientByName(String name);

    List<Patient> getPatientByNameOrBirthDate(String name , LocalDate BirthDate);

    List<Patient> getPatientByBirthDateBetween(LocalDate start , LocalDate end);

    List<Patient> getPatientByNameContaining(String query);

    List<Patient> getPatientByNameContainingOrderByIdDesc(String query);

    @Query("SELECT p FROM Patient p where p.bloodGroup=?1")
    List<Patient> getByBlood(@Param("bloodGroup") BloodGroup bloodGroup);

    @Query("SELECT p FROM Patient p where p.birthDate> :birthDate")
    List<Patient> getByBornAfterDate(@Param("birthDate") LocalDate birthdate);

//    @Query("select p.bloodGroup , Count(p) from Patient p group by p.bloodGroup")
//    List<Object[]> countEachBloodGroupType();

    @Query("SELECT new com.example.hospital.dto.BloodGroupResponseEntity(p.bloodGroup , Count(p)) from Patient p group by p.bloodGroup")
    List<BloodGroupResponseEntity> countEachBloodGroupType();

    @Query(value = "select * from patient",nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name=:name where p.id=:id")
    int updateNameWithId(@Param("name")String name , @Param("id")Long id);

    @Query("SELECT p from Patient p LEFT JOIN FETCH p.appointments ")
    List<Patient> findAllPatientWithAppointment();
}
