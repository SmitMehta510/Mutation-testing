package com.TranquilMind.Service.ServiceImpl;

import com.TranquilMind.dto.AppointmentDto;
import com.TranquilMind.dto.AppointmentListDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Appointment;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.User;
import com.TranquilMind.repository.AppointmentRepository;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.serviceImpl.AppointmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceImplTest {

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAppointmentsForPatient_shouldReturnAppointmentList() {
        
        Long patientUserId = 1L;
        Patient patient = new Patient();
        patient.setPatientId(101L);
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        patient.setUser(user);

        Doctor doctor = new Doctor();
        doctor.setDoctorId(201L);
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setPassword("password1");
        doctor.setUser(user1);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        when(patientService.getPatientByUserId(patientUserId)).thenReturn(patient);
        when(appointmentRepository.appointmentsForPatient(patient.getPatientId()))
                .thenReturn(List.of(appointment));

        List<AppointmentListDto> result = appointmentService.getAppointmentsForPatient(patientUserId);

        assertEquals(1, result.size());
        verify(patientService, times(1)).getPatientByUserId(patientUserId);
        verify(appointmentRepository, times(1)).appointmentsForPatient(patient.getPatientId());
    }

    @Test
    void getAppointmentsForDoctor_shouldReturnAppointmentList() {

        Patient patient = new Patient();
        patient.setPatientId(101L);
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        patient.setUser(user);

        Doctor doctor = new Doctor();
        doctor.setDoctorId(201L);
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setPassword("password1");
        doctor.setUser(user1);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        when(doctorService.getDoctorByUserId(doctor.getDoctorId())).thenReturn(doctor);
        when(appointmentRepository.appointmentsForDoctor(doctor.getDoctorId()))
                .thenReturn(List.of(appointment));

        List<AppointmentListDto> result = appointmentService.getAppointmentsForDoctor(doctor.getDoctorId());

        assertEquals(1, result.size());
        verify(doctorService, times(1)).getDoctorByUserId(doctor.getDoctorId());
        verify(appointmentRepository, times(1)).appointmentsForDoctor(doctor.getDoctorId());
    }

    @Test
    void newAppointment_shouldCreateAndReturnAppointment() {
        
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setDoctorId(201L);
        appointmentDto.setPatientId(101L);
        appointmentDto.setDate(LocalDate.now());
        appointmentDto.setStartTime(LocalTime.of(10, 0));
        appointmentDto.setEndTime(LocalTime.of(11, 0));
        appointmentDto.setDescription("Checkup");

        Doctor doctor = new Doctor();
        Patient patient = new Patient();

        when(doctorService.getDoctorByUserId(appointmentDto.getDoctorId())).thenReturn(doctor);
        when(patientService.getPatientByUserId(appointmentDto.getPatientId())).thenReturn(patient);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Appointment result = appointmentService.newAppointment(appointmentDto);

        assertNotNull(result);
        assertEquals("Checkup", result.getDescription());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void getAppointmentsForDoctorByDate_shouldFilterAppointmentsByDate() {

        LocalDate startDate = LocalDate.now();

        Patient patient = new Patient();
        patient.setPatientId(101L);
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        patient.setUser(user);

        Doctor doctor = new Doctor();
        doctor.setDoctorId(201L);
        User user1 = new User();
        user1.setEmail("test1@example.com");
        user1.setPassword("password1");
        doctor.setUser(user1);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(startDate.minusDays(1));

        Appointment appointment2 = new Appointment();
        appointment2.setDate(startDate.plusDays(2));

        when(doctorService.getDoctorByUserId(doctor.getDoctorId())).thenReturn(doctor);
        when(appointmentRepository.fetchAppointmentsByDoctorAndDate(doctor.getDoctorId()))
                .thenReturn(List.of(appointment, appointment2));

        List<AppointmentListDto> result = appointmentService.getAppointmentsForDoctorByDate(doctor.getDoctorId(), startDate);

        assertEquals(1, result.size());
        verify(doctorService, times(1)).getDoctorByUserId(doctor.getDoctorId());
        verify(appointmentRepository, times(1)).fetchAppointmentsByDoctorAndDate(doctor.getDoctorId());
    }

    @Test
    void cancelAppointment_shouldDeleteAppointment() {
        
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        boolean result = appointmentService.cancelAppointment(appointmentId);

        assertTrue(result);
        verify(appointmentRepository, times(1)).delete(appointment);
    }

    @Test
    void cancelAppointment_shouldThrowExceptionWhenAppointmentNotFound() {
        
        Long appointmentId = 1L;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.cancelAppointment(appointmentId));
        verify(appointmentRepository, never()).delete(any());
    }

    @Test
    void appointmentDataByDoctor_shouldReturnAppointmentData() {
        
        Long doctorId = 201L;
        when(appointmentRepository.getPatientDataByDoctorId(doctorId)).thenReturn(5);
        when(appointmentRepository.totalAppointmentsByDoctorId(doctorId)).thenReturn(10);
        when(appointmentRepository.totalPendingAppointmentsByDoctorId(doctorId, LocalDate.now(), LocalTime.now())).thenReturn(3);

        List<Integer> result = appointmentService.appointmentDataByDoctor(doctorId);

        assertEquals(3, result.size());
        assertEquals(5, result.get(0));
        assertEquals(10, result.get(1));

        verify(appointmentRepository, times(1)).getPatientDataByDoctorId(doctorId);
        verify(appointmentRepository, times(1)).totalAppointmentsByDoctorId(doctorId);
    }

    @Test
    void distinctPatientByDoctor_shouldReturnDistinctPatients() {
        
        Long doctorId = 201L;
        when(appointmentRepository.findDistinctPatientByDoctorId(doctorId)).thenReturn(List.of(101L, 102L));

        List<Long> result = appointmentService.distinctPatientByDoctor(doctorId);
        
        assertEquals(2, result.size());
        assertTrue(result.contains(101L));
        assertTrue(result.contains(102L));
        verify(appointmentRepository, times(1)).findDistinctPatientByDoctorId(doctorId);
    }
}
