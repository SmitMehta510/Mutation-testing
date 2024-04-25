package com.TranquilMind.service;

import com.TranquilMind.dto.DoctorDto;
import com.TranquilMind.dto.DoctorRegisterDto;
import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.model.Appointment;
import com.TranquilMind.model.Doctor;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface DoctorService {

    List<DoctorDto> getAllActiveDoctors();

    List<DoctorDto> getAllDoctors();

    boolean deleteDoctor(Long id);

    Doctor updateDoctor(Long id, Doctor doctor);

//    Doctor getDoctorById(Long id);

    Doctor getDoctorByUserId(Long id);

    ResponseEntity<?> createDoctor(DoctorRegisterDto doctorRegisterDto);

    List<DoctorDto> getAllDisabledDoctors();

    List<Appointment> getAppointments(Long id);

    Boolean approveDoctor(Long id, Boolean approve);

    List<Integer> getStatsForDoctor(Long doctorId);

    boolean updatePassword(PasswordDto passwordDto);

    boolean getIsSenior(Long doctorId);

    List<Long> distinctPatientByDoctorId(Long doctorId);
}
