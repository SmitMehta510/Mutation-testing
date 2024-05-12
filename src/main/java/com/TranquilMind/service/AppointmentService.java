package com.TranquilMind.service;

import com.TranquilMind.dto.AppointmentDto;
import com.TranquilMind.dto.AppointmentListDto;
import com.TranquilMind.model.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    List<AppointmentListDto> getAppointmentsForPatient(Long id);

    List<AppointmentListDto> getAppointmentsForDoctor(Long id);

    Appointment newAppointment(AppointmentDto appointmentDto);

    List<AppointmentListDto> getAppointmentsForDoctorByDate(Long id, LocalDate date);

    boolean cancelAppointment(Long id);

    Appointment updateAppointment(Appointment appointment);

    List<Integer> appointmentDataByDoctor(Long doctorId);

    List<Long> distinctPatientByDoctor(Long doctorId);
}
