package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.AppointmentDto;
import com.TranquilMind.dto.AppointmentListDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Appointment;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.model.Patient;
import com.TranquilMind.repository.AppointmentRepository;
import com.TranquilMind.service.AppointmentService;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    UserService userService;

    @Override
    public List<AppointmentListDto> getAppointmentsForPatient(Long id) {
        Patient patient = patientService.getPatientByUserId(id);
        return appointmentRepository.appointmentsForPatient(patient.getPatientId())
                .stream().map(Appointment::toListDto).toList();
    }

    @Override
    public List<AppointmentListDto> getAppointmentsForDoctor(Long id) {
        Doctor doctor = doctorService.getDoctorByUserId(id);
        return appointmentRepository.appointmentsForDoctor(doctor.getDoctorId())
                .stream().map(Appointment::toListDto).toList();
    }

    @Override
    public Appointment newAppointment(AppointmentDto appointmentDto) {

        Doctor doctor = doctorService.getDoctorByUserId(appointmentDto.getDoctorId());

        Patient patient = patientService.getPatientByUserId(appointmentDto.getPatientId());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(appointmentDto.getDate());
        appointment.setStartTime(appointmentDto.getStartTime());
        appointment.setEndTime(appointmentDto.getEndTime());
        appointment.setDescription(appointmentDto.getDescription());
        appointment.setRemarks(appointmentDto.getRemarks());

        appointmentRepository.save(appointment);

        return appointment;
    }

    @Override
    public List<AppointmentListDto> getAppointmentsForDoctorByDate(Long id, LocalDate startDate) {

        Doctor doctor = doctorService.getDoctorByUserId(id);
        LocalDate endDate = startDate.minusDays(3);
        List<Appointment> appointmentList = appointmentRepository.fetchAppointmentsByDoctorAndDate(doctor.getDoctorId());
        return appointmentList.stream().filter(
                (app) -> app.getDate().isBefore(startDate.plusDays(1)) && app.getDate().isAfter(endDate.minusDays(1)))
                .map(Appointment::toListDto).toList();

    }

    @Override
    public boolean cancelAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointmentRepository.delete(appointment);
        return true;
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return null;
    }

    @Override
    public List<Integer> appointmentDataByDoctor(Long doctorId) {
        List<Integer> appointmentData = new ArrayList<>();
        appointmentData.add(appointmentRepository.getPatientDataByDoctorId(doctorId));
        appointmentData.add(appointmentRepository.totalAppointmentsByDoctorId(doctorId));
        appointmentData.add(appointmentRepository.totalPendingAppointmentsByDoctorId(doctorId,LocalDate.now(), LocalTime.now()));
        return appointmentData;
    }

    @Override
    public List<Long> distinctPatientByDoctor(Long doctorId) {
        return appointmentRepository.findDistinctPatientByDoctorId(doctorId);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id :" + id));
    }
}
