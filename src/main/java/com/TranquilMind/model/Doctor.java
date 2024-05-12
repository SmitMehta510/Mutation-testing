package com.TranquilMind.model;

import com.TranquilMind.dto.DoctorDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long doctorId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    User user;

    @Column(nullable = false)
    String firstName;

    String middleName;
    @Column(nullable = false)
    String lastName;
    Integer age;
    @Column(nullable = false)
    Gender gender;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String image;

    String mobileNo;
    @Column(nullable = false)
    String licenceNo;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String description;

    Double consultationFee;
    @Column(nullable = false)
    Integer experience;
    Boolean isSenior;
    Boolean isDisabled;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    List<Appointment> appointmentList;


    public Doctor(User user, String firstName, String middleName, String lastName, Integer age, Gender gender,
                  String mobileNo, String licenceNo, String description, Double consultationFee,
                  Integer experience, Boolean isSenior, Boolean isDisabled, String image) {
        this.user = user;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.mobileNo = mobileNo;
        this.licenceNo = licenceNo;
        this.description = description;
        this.consultationFee = consultationFee;
        this.experience = experience;
        this.isSenior = isSenior;
        this.isDisabled = isDisabled;
        this.image = image;
    }

    public DoctorDto toDto(){
        return new DoctorDto(user.getUserId(), user.getEmail(), firstName,middleName,lastName,age,gender,image,
                mobileNo,licenceNo,description,consultationFee,experience,isSenior,isDisabled);
    }


}
