package com.TranquilMind.controller;

import com.TranquilMind.dto.DoctorRegisterDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.utilities.AppLogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

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
        Doctor doctor = null;
        try {
            doctor = doctorService.getDoctorByUserId(id);
        } catch (ResourceNotFoundException e) {
            logger.error(e.toString(),e.getMessage(),e);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(doctor);
    }





}