package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.*;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.PatientRepository;
import com.TranquilMind.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @Autowired
    private QuestionService questionService;


    @Override
    public List<PatientDto> getAllPatients() {
        List<PatientDto> list = new ArrayList<>();
        patientRepository.findAll().forEach(patient -> list.add(patient.toDto()));
        return list;
    }

    @Override
    public boolean deletePatient(Long id) {
        boolean isDeleted = true;
        Patient patient = patientRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id));

        patientRepository.delete(patient);

        return isDeleted;
    }

    //TODO change update method for patient and doctor
    @Override
    public Patient updatePatient(Long id, PatientRegisterDto patientDetails) {
        Patient patient = patientRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id));

        patient.setFirstName(patientDetails.getFirstName());
        patient.setMiddleName(patientDetails.getMiddleName());
        patient.setLastName(patientDetails.getLastName());
        patient.setAge(patientDetails.getAge());

        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatientByUserId(Long id) throws ResourceNotFoundException {
        return patientRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id));
    }

    @Override
    public PatientDto getPatientDtoByUserId(Long id) {
        Patient patient = patientRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id));
        return patient.toDto();
    }

    @Override
    public Patient createPatient(PatientRegisterDto patientRegisterDto) {

        AuthDto authDto = new AuthDto(patientRegisterDto.getEmail(), patientRegisterDto.getPassword());

        RegisterDto registerDto = userService.register(authDto, RoleName.PATIENT);

        if (registerDto.getResponse().getStatusCode().is2xxSuccessful()) {
            Patient patient = getPatient(patientRegisterDto, registerDto);
            patientRepository.save(patient);
            return patient;
        } else {
            throw new ResourceNotFoundException("Patient creation unsuccessful");
        }
    }

    private static Patient getPatient(PatientRegisterDto patientRegisterDto, RegisterDto registerDto) {
        Patient patient = new Patient();
        patient.setUser(registerDto.getUser());
        patient.setFirstName(patientRegisterDto.getFirstName());
        patient.setMiddleName(patientRegisterDto.getMiddleName());
        patient.setLastName(patientRegisterDto.getLastName());
        patient.setAge(patientRegisterDto.getAge());
        patient.setGender(patientRegisterDto.getGender());
        patient.setMobileNo(patientRegisterDto.getMobileNo());
        patient.setImage(patientRegisterDto.getImage());
        return patient;
    }

    @Override
    public List<PostDto> getPosts(Long userId) {
        return postService.getPostsByUserId(userId);
    }

    @Override
    public List<QuestionDto> getQuestions(Long userId) {
        return questionService.getQuestionByUserId(userId);
    }

    @Override
    public boolean updatePassword(PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto);
    }

}
