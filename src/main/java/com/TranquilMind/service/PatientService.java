package com.TranquilMind.service;

import com.TranquilMind.dto.PatientDto;
import com.TranquilMind.dto.PatientRegisterDto;
import com.TranquilMind.model.Patient;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface PatientService {

    List<PatientDto> getAllPatients();

    boolean deletePatient(Long id);

    Patient updatePatient(Long id, Patient patient);

    PatientDto getPatientById(Long id);

    Patient getPatientByUserId(Long id);

    ResponseEntity<?> createPatient(PatientRegisterDto patientRegisterDto);

}
