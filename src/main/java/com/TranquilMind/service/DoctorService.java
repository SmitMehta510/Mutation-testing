package com.TranquilMind.service;

import com.TranquilMind.dto.DoctorDto;
import com.TranquilMind.dto.DoctorRegisterDto;
import com.TranquilMind.model.Appointment;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface DoctorService {

//    List<Doctor> getAllDoctors();

    List<DoctorDto> getAllDoctors();

    boolean deleteDoctor(Long id);

    Doctor updateDoctor(Long id, Doctor doctor);

    Doctor getDoctorById(Long id);

    Doctor getDoctorByUserId(Long id);

    ResponseEntity<?> createDoctor(DoctorRegisterDto doctorRegisterDto);

    List<Doctor> getDisabledDoctors();

    List<Appointment> getAppointments(Long id);

}