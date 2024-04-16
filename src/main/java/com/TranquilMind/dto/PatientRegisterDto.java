package com.TranquilMind.dto;

import com.TranquilMind.model.Gender;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientRegisterDto implements Serializable {

    String email;
    String password;
    String firstName;
    String middleName;
    String lastName;
    Integer age;
    Gender gender;
    String mobileNo;
    String image;
}
