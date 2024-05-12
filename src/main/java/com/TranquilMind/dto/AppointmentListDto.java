package com.TranquilMind.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentListDto {

    Long appointmentId;
    
    PatientDto patient;

    DoctorDto doctor;

    LocalDate date;

    LocalTime startTime;

    LocalTime endTime;

    String description;

    String remarks;
}
