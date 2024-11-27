package com.TranquilMind.service;

import com.TranquilMind.dto.*;
import com.TranquilMind.model.Patient;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PatientService {

    List<PatientDto> getAllPatients();

    boolean deletePatient(Long id);

    Patient updatePatient(Long id, PatientRegisterDto patientRegisterDto);

    Patient getPatientByUserId(Long id);

    PatientDto getPatientDtoByUserId(Long id);

    Patient createPatient(PatientRegisterDto patientRegisterDto);

    List<PostDto> getPosts(Long userId);

    List<QuestionDto> getQuestions(Long userId);

    boolean updatePassword(PasswordDto passwordDto);

}
