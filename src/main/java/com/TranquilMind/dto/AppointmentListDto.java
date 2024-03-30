package com.TranquilMind.dto;

import com.TranquilMind.model.Gender;
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


    String firstName;
    String middleName;
    String lastName;
    Integer age;
    Gender gender;
    LocalDate date;

    LocalTime startTime;

    LocalTime endTime;

    String description;

    String remarks;
}
