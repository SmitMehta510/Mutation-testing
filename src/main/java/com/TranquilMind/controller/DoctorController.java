package com.TranquilMind.controller;

import com.TranquilMind.dto.DoctorRegisterDto;
import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.utilities.AppLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    Logger logger = AppLogger.getLogger();

    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorRegisterDto doctorRegisterDto) {
        return doctorService.createDoctor(doctorRegisterDto);
    }

    @GetMapping("/doctorbyid/{id}")
    public ResponseEntity<?> doctorByUserId(@PathVariable Long id) {
        Doctor doctor;
        try {
            doctor = doctorService.getDoctorByUserId(id);
        } catch (ResourceNotFoundException e) {
            logger.error(e.toString(),e.getMessage(),e);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(doctor);
    }

    @PutMapping("/update-doctor/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, Doctor doctor){
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctor), HttpStatus.OK);
    }

    @GetMapping("/get-stats/{doctorId}")
    public ResponseEntity<?> getStatsForDoctor(@PathVariable Long doctorId){
        return new ResponseEntity<>(doctorService.getStatsForDoctor(doctorId),HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(doctorService.updatePassword(passwordDto),HttpStatus.OK);
    }

    @GetMapping("/is-senior/{doctorId}")
    public ResponseEntity<?> isSenior(@PathVariable Long doctorId){
        return new ResponseEntity<>(doctorService.getIsSenior(doctorId),HttpStatus.OK);
    }

    @GetMapping("/distinct-patient/{doctorId}")
    public ResponseEntity<?> distinctPatient(@PathVariable Long doctorId){
        return new ResponseEntity<>(doctorService.distinctPatientByDoctorId(doctorId),HttpStatus.OK);
    }
}