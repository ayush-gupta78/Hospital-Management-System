package com.example.hospital;

import com.example.hospital.Entity.Patient;
import com.example.hospital.dto.BloodGroupResponseEntity;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.PatientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.util.List;


@SpringBootTest
public class PatientTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Test
    public void testPatientRepositor(){
        List<Patient> patientList=patientRepository.findAllPatientWithAppointment();
        IO.println(patientList);
    }

    @Test
    public void testTransactionMethods(){
//        Patient patient=patientService.getPatientById(1);
//        IO.println(patient);

//        Patient patient=patientRepository.getPatientByName("Aarav Sharma");
//        IO.println(patient);

//        List <Patient> patientList=patientRepository.getPatientByNameOrBirthDate("Neha Iyer", LocalDate.of(1993,7,11));
//        for(Patient patient: patientList)
//            IO.println(patient);
//
//        patientList.clear();
//        patientList=patientRepository.getPatientByNameContainingOrderByIdDesc("Si");
//
//        for(Patient patient: patientList)
//            IO.println(patient);

//        List<Patient> patientList=patientRepository.getByBornAfterDate(LocalDate.of(1990,7,11));
//
//        for(Patient patient: patientList)
//           IO.println(patient);

//        List <Object[]> bloodGroupList=patientRepository.countEachBloodGroupType();
//
//        for(Object[] objects : bloodGroupList)
//            System.out.println(objects[0]+" "+ objects[1]);

        Page<Patient> patientList=patientRepository.findAllPatients( PageRequest.of(0,2, Sort.by("name")));

        for(Patient patient: patientList)
        IO.println(patient);
//
//        int rowsUpdated=patientRepository.updateNameWithId("ROHAN",1L);
//        IO.println(rowsUpdated);

//        List <BloodGroupResponseEntity> bloodGroupList=patientRepository.countEachBloodGroupType();
//
//        for(BloodGroupResponseEntity bloodGroupResponse : bloodGroupList)
//            System.out.println(bloodGroupResponse);
    }

}
