package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.AuthDto;
import com.TranquilMind.dto.PatientDto;
import com.TranquilMind.dto.PatientRegisterDto;
import com.TranquilMind.dto.RegisterDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.RoleName;
import com.TranquilMind.repository.PatientRepository;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.UserService;
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

    @Override
    public List<PatientDto> getAllPatients() {
        List<PatientDto> list = new ArrayList<>();
        patientRepository.findAll().forEach(patient -> list.add(new Patient().toDto(patient)));
        return list;
    }

    @Override
    public boolean deletePatient(Long id) {
        boolean isDeleted = true;
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id));

        patientRepository.delete(patient);

        return isDeleted;
    }

    //TODO change update method for patient and doctor
    @Override
    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id));

        patient.setFirstName(patientDetails.getFirstName());
        patient.setLastName(patientDetails.getLastName());
        patient.setAge(patientDetails.getAge());

        return null;
    }

    @Override
    public PatientDto getPatientById(Long id) throws ResourceNotFoundException {
        return new Patient().toDto(patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id)));
    }

    @Override
    public Patient getPatientByUserId(Long id) {
        return patientRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not exist with id :" + id));
    }

    @Override
    public ResponseEntity<?> createPatient(PatientRegisterDto patientRegisterDto) {

        AuthDto authDto = new AuthDto(patientRegisterDto.getEmail(), patientRegisterDto.getPassword());

        RegisterDto registerDto = userService.register(authDto, RoleName.PATIENT);

        if (registerDto.getResponse().getStatusCode().is2xxSuccessful()) {
            Patient patient = new Patient();
            patient.setUser(registerDto.getUser());
            patient.setFirstName(patientRegisterDto.getFirstName());
            patient.setMiddleName(patientRegisterDto.getMiddleName());
            patient.setLastName(patientRegisterDto.getLastName());
            patient.setAge(patientRegisterDto.getAge());
            patient.setGender(patientRegisterDto.getGender());
            patient.setMobileNo(patientRegisterDto.getMobileNo());
            patientRepository.save(patient);
        } else {
            throw new ResourceNotFoundException("Patient creation unsuccessful");
        }

        return registerDto.getResponse();
    }
}
