package com.TranquilMind.dto;

import com.TranquilMind.model.Gender;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorRegisterDto {

    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String firstName;
    String middleName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    Integer age;
    @Column(nullable = false)
    Gender gender;
    String image;

    String mobileNo;
    @Column(nullable = false)
    String licenceNo;
    String description;
    Double consultationFee;
    @Column(nullable = false)
    Integer experience;
    Boolean isSenior;
}
