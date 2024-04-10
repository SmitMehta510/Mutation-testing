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
import java.util.List;
import java.util.Objects;

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
    public List<Appointment> getAppointmentsForPatient(Long id) {
        return appointmentRepository.findAll().stream().filter(app -> Objects.equals(app.getPatient().getUser().getUserId(), id)).toList();
    }

    @Override
    public List<AppointmentListDto> getAppointmentsForDoctor(Long id) {
//        return appointmentRepository.findAll().stream().filter(app -> Objects.equals(app.getDoctor().getUser().getUserId(), id)).toList();
        return appointmentRepository.findAll()
                .stream()
                .filter(app -> Objects.equals(app.getDoctor().getUser().getUserId(), id)).toList()
                .stream()
                .map(Appointment::toListDto).toList();
//        return doctorService.getAppointments(id);
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
    public List<AppointmentListDto> getAppointmentsForDoctorByDate(Long id, LocalDate date) {

        List<Appointment> appointmentList = appointmentRepository.fetchByDate(date);

        return appointmentList.stream()
                .filter(appointment -> Objects.equals(appointment.getDoctor().getUser().getUserId(), id)).toList()
                .stream().map(Appointment::toListDto).toList();
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

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not exist with id :" + id));
    }
}
