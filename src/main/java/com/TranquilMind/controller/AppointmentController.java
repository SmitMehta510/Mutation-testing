package com.TranquilMind.controller;

import com.TranquilMind.dto.AppointmentDto;
import com.TranquilMind.dto.AppointmentListDto;
import com.TranquilMind.model.Appointment;
import com.TranquilMind.service.AppointmentService;
import com.TranquilMind.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/patient-appointments/{id}")
    public ResponseEntity<?> getPatientAppointments(@PathVariable Long id){
        return new ResponseEntity<>(appointmentService.getAppointmentsForPatient(id),HttpStatus.OK);
    }

    @GetMapping("/doctor-appointments/{id}")
    public ResponseEntity<?> getDoctorAppointments(@PathVariable Long id){
        List<AppointmentListDto> appointments = appointmentService.getAppointmentsForDoctor(id);

        if(appointments!=null){
            return new ResponseEntity<>(appointments,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/doctor-appointments/{id}/date/{date}")
    public ResponseEntity<?> getDoctorAppointmentsByDate(@PathVariable Long id, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return new ResponseEntity<>(appointmentService.getAppointmentsForDoctorByDate(id,date),HttpStatus.OK);
    }

    @GetMapping("/doctors")
    public ResponseEntity<?> getDoctorsList(){
        return new ResponseEntity<>(doctorService.getAllActiveDoctors(),HttpStatus.OK);
    }

    @PostMapping("/new-appointment")
    public ResponseEntity<?> newAppointment(@RequestBody AppointmentDto appointmentDto){
        Appointment appointment = appointmentService.newAppointment(appointmentDto);

        if(appointment!=null){
            return new ResponseEntity<>(appointment,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.GONE);
    }

}
