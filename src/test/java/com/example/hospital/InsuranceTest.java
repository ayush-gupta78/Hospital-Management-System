package com.example.hospital;

import com.example.hospital.Entity.Appointment;
import com.example.hospital.Entity.Insurance;
import com.example.hospital.Entity.Patient;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void TestInsurance(){
        Insurance insurance=Insurance.builder().policyNumber("ICICI_1234")
                .provider("ICICI").validUntil(LocalDate.of(2029,8,10)).build();

        Patient patient=insuranceService.AssignInsuranceToPatient(insurance,1L);
        IO.println(patient);

        var newPatient=insuranceService.cancelInsurance(patient.getId());

    }

    @Test
    public void TestAppointment(){
        Appointment appointment=Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026,3,10,16,0,0))
                .reason("HeadAche").build();

        //Appointment newAppointment=appointmentService.createNewAppointment(appointment,2L,4L);

//        IO.println(newAppointment);
//
//        var updatedAppointment=appointmentService.reAssignToNewDoctor(newAppointment.getId(), 1L);
//        IO.println(updatedAppointment);
    }
}
