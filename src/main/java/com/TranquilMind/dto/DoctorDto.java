package com.TranquilMind.dto;

import com.TranquilMind.model.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDto {

    Long userId;
    String email;
    String firstName;
    String middleName;
    String lastName;
    Integer age;
    Gender gender;
    String image;
    String mobileNo;
    String licenceNo;
    String description;
    Double consultationFee;
    Integer experience;
    Boolean isSenior;
    Boolean isDisabled;

}
