package com.TranquilMind.dto;

import com.TranquilMind.model.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientDto {
    String email;
    Long userId;
    String firstName;
    String middleName;
    String lastName;
    Integer age;
    String mobileNo;
    Gender gender;
    String image;
}
