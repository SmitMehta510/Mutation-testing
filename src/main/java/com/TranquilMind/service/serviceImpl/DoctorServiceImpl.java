package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.*;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Appointment;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.RoleName;
import com.TranquilMind.repository.DoctorRepository;
import com.TranquilMind.service.AppointmentService;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public List<DoctorDto> getAllActiveDoctors() {
        return doctorRepository.getDoctors(false)
                .stream()
                .filter((doctor -> doctor.getIsSenior().equals(false)))
                .map(Doctor::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(Doctor::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteDoctor(Long id) {
        boolean isDeleted = true;
        Doctor doctor = getDoctorByUserId(id);

        doctorRepository.delete(doctor);

        return isDeleted;
    }

    //TODO change update method for patient and doctor
    @Override
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = getDoctorByUserId(id);

        doctor.setFirstName(doctorDetails.getFirstName());
        doctor.setLastName(doctorDetails.getLastName());
        doctor.setConsultationFee(doctorDetails.getConsultationFee());
        doctor.setMobileNo(doctorDetails.getMobileNo());
        doctor.setAge(doctorDetails.getAge());
        doctor.setDescription(doctorDetails.getDescription());

        return null;
    }

//    @Override
//    public Doctor getDoctorById(Long id) throws ResourceNotFoundException {
//        return doctorRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id :" + id));
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + id));
//    }

    @Override
    public Doctor getDoctorByUserId(Long id) throws ResourceNotFoundException {
        return doctorRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not exist with id :" + id));
    }

    @Override
    public ResponseEntity<?> createDoctor(DoctorRegisterDto doctorRegisterDto) {

        AuthDto authDto = new AuthDto(doctorRegisterDto.getEmail(), doctorRegisterDto.getPassword());

        RegisterDto registerDto = userService.register(authDto, RoleName.DOCTOR);

        if (registerDto.getResponse().getStatusCode().is2xxSuccessful()) {
            Doctor doctor = new Doctor(registerDto.getUser(), doctorRegisterDto.getFirstName(),
                    doctorRegisterDto.getMiddleName(), doctorRegisterDto.getLastName(), doctorRegisterDto.getAge(),
                    doctorRegisterDto.getGender(), doctorRegisterDto.getMobileNo(), doctorRegisterDto.getLicenceNo(),
                    doctorRegisterDto.getDescription(), doctorRegisterDto.getConsultationFee(),
                    doctorRegisterDto.getExperience(), doctorRegisterDto.getIsSenior(),
                    true, doctorRegisterDto.getImage());
            doctorRepository.save(doctor);
        }
//        } else {
//            throw new ResourceNotFoundException("Doctor creation unsuccessful");
//        }

        return registerDto.getResponse();
    }

    public List<Appointment> getAppointments(Long id) {
        Optional<Doctor> doc = doctorRepository.findByUserId(id);

        if (doc.isPresent()) {
            Doctor doctor = doc.get();
            return doctor.getAppointmentList();
        } else {
            return null;
        }

    }

    @Override
    public Boolean approveDoctor(Long id, Boolean approve) {
        Doctor doctor = getDoctorByUserId(id);

        if (doctor != null) {
            doctor.setIsDisabled(!approve);
            doctorRepository.save(doctor);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<Integer> getStatsForDoctor(Long doctorId) {
        return appointmentService.appointmentDataByDoctor(doctorId);
    }

    @Override
    public boolean updatePassword(PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto);
    }

    @Override
    public boolean getIsSenior(Long doctorId) {
        Doctor doctor = getDoctorByUserId(doctorId);
        if(doctor != null){
            return doctor.getIsSenior();
        }
        return false;
    }

    @Override
    public List<Long> distinctPatientByDoctorId(Long doctorId) {
        return appointmentService.distinctPatientByDoctor(doctorId);
    }

    @Override
    public List<DoctorDto> getAllDisabledDoctors() {
        return doctorRepository.getDoctors(true)
                .stream()
                .map(Doctor::toDto)
                .collect(Collectors.toList());
    }
}
