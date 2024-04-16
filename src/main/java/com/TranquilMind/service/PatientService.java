package com.TranquilMind.service;

import com.TranquilMind.dto.PatientDto;
import com.TranquilMind.dto.PatientRegisterDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.Quiz;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAllPatients();

    boolean deletePatient(Long id);

    Patient updatePatient(Long id, PatientRegisterDto patientRegisterDto);

    Patient getPatientByUserId(Long id);

    PatientDto getPatientDtoByUserId(Long id);

    ResponseEntity<?> createPatient(PatientRegisterDto patientRegisterDto);

    List<Quiz> getQuizzes(Long id);

    List<PostDto> getPosts(Long userId);
}
