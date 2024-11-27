package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.*;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.*;
import com.TranquilMind.repository.DoctorRepository;
import com.TranquilMind.service.AppointmentService;
import com.TranquilMind.service.UserService;
import com.TranquilMind.service.serviceImpl.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserService userService;

    @Mock
    private AppointmentService appointmentService;

    @Test
    void getAllActiveDoctors_shouldReturnActiveDoctors() {
        
        Doctor doctor1 = new Doctor();
        doctor1.setIsSenior(false);
        User user1 = new User();
        user1.setUserId(1L);
        user1.setEmail("testuser1@gmail.com");
        doctor1.setUser(user1);
        Doctor doctor2 = new Doctor();
        doctor2.setIsSenior(false);
        User user2 = new User();
        user2.setUserId(2L);
        user2.setEmail("testuser2@gmail.com");
        doctor2.setUser(user2);
        List<Doctor> doctors = List.of(doctor1, doctor2);

        when(doctorRepository.getDoctors(false)).thenReturn(doctors);

        List<DoctorDto> result = doctorService.getAllActiveDoctors();
        
        assertEquals(2, result.size());
        verify(doctorRepository, times(1)).getDoctors(false);
    }

    @Test
    void getAllActiveDoctors_shouldReturnEmptyListWhenNoDoctors() {
        
        when(doctorRepository.getDoctors(false)).thenReturn(List.of());

        List<DoctorDto> result = doctorService.getAllActiveDoctors();

        assertTrue(result.isEmpty());
        verify(doctorRepository, times(1)).getDoctors(false);
    }


    @Test
    void getAllDoctors_shouldReturnAllDoctors() {
        
        Doctor doctor1 = new Doctor();
        User user1 = new User();
        user1.setUserId(1L);
        user1.setEmail("testuser1@gmail.com");
        doctor1.setUser(user1);
        Doctor doctor2 = new Doctor();
        User user2 = new User();
        user2.setUserId(2L);
        user2.setEmail("testuser2@gmail.com");
        doctor2.setUser(user2);
        List<Doctor> doctors = List.of(doctor1, doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        List<DoctorDto> result = doctorService.getAllDoctors();

        assertEquals(2, result.size());
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void deleteDoctor_shouldDeleteDoctor() {
        
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.of(doctor));

        boolean result = doctorService.deleteDoctor(doctorId);

        assertTrue(result);
        verify(doctorRepository, times(1)).findByUserId(doctorId);
        verify(doctorRepository, times(1)).delete(doctor);
    }

    @Test
    void deleteDoctor_shouldDeleteCorrectDoctor() {
        
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.of(doctor));

        boolean result = doctorService.deleteDoctor(doctorId);

        assertTrue(result);
        verify(doctorRepository, times(1)).delete(doctor);
    }


    @Test
    void deleteDoctor_shouldThrowExceptionWhenDoctorNotFound() {
        Long doctorId = 1L;

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.deleteDoctor(doctorId));
        verify(doctorRepository, times(1)).findByUserId(doctorId);
        verify(doctorRepository, never()).delete(any());
    }

    @Test
    void updateDoctor_shouldUpdateDoctorDetails() {
        
        Long doctorId = 1L;
        Doctor existingDoctor = new Doctor();
        User user1 = new User();
        user1.setUserId(1L);
        user1.setEmail("testuser1@gmail.com");
        existingDoctor.setUser(user1);
        existingDoctor.setDoctorId(doctorId);

        Doctor updatedDetails = new Doctor();
        updatedDetails.setFirstName("John");
        updatedDetails.setLastName("Jane");
        updatedDetails.setMiddleName("Smith");
        updatedDetails.setConsultationFee(500.0);
        updatedDetails.setDescription("Oncologist");
        updatedDetails.setAge(45);
        updatedDetails.setMobileNo("1234567890");


        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.of(existingDoctor));

        Doctor result = doctorService.updateDoctor(doctorId, updatedDetails);

        assertEquals("John", result.getFirstName());
        assertEquals("Jane", result.getLastName());
        assertEquals(500.0, result.getConsultationFee());
        assertEquals("Oncologist", result.getDescription());
        assertEquals(45, result.getAge());
        verify(doctorRepository, times(1)).findByUserId(doctorId);
    }

    @Test
    void createDoctor_shouldCreateDoctor() {
        
        DoctorRegisterDto doctorRegisterDto = new DoctorRegisterDto();
        doctorRegisterDto.setEmail("doctor@example.com");
        doctorRegisterDto.setPassword("password");
        doctorRegisterDto.setFirstName("John");
        doctorRegisterDto.setLastName("Jane");
        doctorRegisterDto.setMiddleName("Smith");
        doctorRegisterDto.setConsultationFee(500.0);
        doctorRegisterDto.setDescription("Oncologist");
        doctorRegisterDto.setAge(45);
        doctorRegisterDto.setMobileNo("1234567890");
        doctorRegisterDto.setPassword("password");
        doctorRegisterDto.setImage("Image");
        doctorRegisterDto.setGender(Gender.MALE);
        doctorRegisterDto.setLicenceNo("J5TH");
        doctorRegisterDto.setIsSenior(false);
        doctorRegisterDto.setExperience(2);

        AuthDto authDto = new AuthDto("doctor@example.com", "password");
        RegisterDto registerDto = new RegisterDto(new User(), new ResponseEntity<>(HttpStatus.OK));

        when(userService.register(authDto, RoleName.DOCTOR)).thenReturn(registerDto);
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

       Doctor doctor  = doctorService.createDoctor(doctorRegisterDto);

       assertEquals("John", doctor.getFirstName());
       assertEquals("Jane", doctor.getLastName());
       assertEquals(500.0, doctor.getConsultationFee());
       assertEquals("Oncologist", doctor.getDescription());
       assertEquals(45, doctor.getAge());
       assertEquals(Gender.MALE, doctor.getGender());
       assertEquals("1234567890",doctor.getMobileNo());
       assertEquals("Image",doctor.getImage());
       assertEquals("J5TH",doctor.getLicenceNo());
       assertEquals(false,doctor.getIsSenior());
       assertEquals(2,doctor.getExperience());
       assertEquals("Smith", doctor.getMiddleName());
       assertEquals(true, doctor.getIsDisabled());

        verify(userService, times(1)).register(authDto, RoleName.DOCTOR);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }


    @Test
    void approveDoctor_shouldApproveDoctor() {
        
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.of(doctor));

        boolean result = doctorService.approveDoctor(doctorId, true);

        assertTrue(result);
        assertFalse(doctor.getIsDisabled());
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void approveDoctor_shouldThrowExceptionWhenDoctorNotFound() {
        Long doctorId = 1L;

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.approveDoctor(doctorId, true));

    }

    @Test
    void getAppointments_shouldReturnAppointments() {
        
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        List<Appointment> appointments = List.of(new Appointment(), new Appointment());
        doctor.setAppointmentList(appointments);

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.of(doctor));

        List<Appointment> result = doctorService.getAppointments(doctorId);
        
        assertEquals(2, result.size());
        verify(doctorRepository, times(1)).findByUserId(doctorId);
    }

    @Test
    void getAppointments_shouldReturnEmptyListWhenNoAppointments() {
        
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setAppointmentList(new ArrayList<>());

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.of(doctor));

        List<Appointment> result = doctorService.getAppointments(doctorId);
        
        assertTrue(result.isEmpty());
        verify(doctorRepository, times(1)).findByUserId(doctorId);
    }



    @Test
    void getIsSenior_shouldReturnTrueIfSenior() {
        
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setIsSenior(true);

        when(doctorRepository.findByUserId(doctorId)).thenReturn(Optional.of(doctor));

        boolean result = doctorService.getIsSenior(doctorId);

        assertTrue(result);
        verify(doctorRepository, times(1)).findByUserId(doctorId);
    }
}
