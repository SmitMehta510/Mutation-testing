package com.TranquilMind.service;

import com.TranquilMind.dto.*;
import com.TranquilMind.model.EnrolledCourse;
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

    List<QuestionDto> getQuestions(Long userId);

    boolean updatePassword(PasswordDto passwordDto);

    EnrolledCourse enrollCourse(Long patientId, Long courseId);

    boolean markComplete(Long patientId, Long courseId);

    List<EnrollCourseDto> enrollCourses(Long patientId);

    EnrollCourseDto taskComplete(Long patientId, Long courseId);
}
