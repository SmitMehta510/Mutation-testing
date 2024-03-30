package com.TranquilMind.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppointmentDto {

    Long doctorId;

    Long patientId;

    LocalDate date;

    LocalTime startTime;

    LocalTime endTime;

    String description;

    String remarks;

}
