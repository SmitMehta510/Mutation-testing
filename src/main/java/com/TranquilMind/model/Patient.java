package com.TranquilMind.model;

import com.TranquilMind.dto.PatientDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long patientId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    User user;

    @Column(nullable = false)
    String firstName;
    String middleName;
    String lastName;
    Integer age;
    String mobileNo;
    @Column(nullable = false)
    Gender gender;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String image;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    List<Appointment> appointmentList;

//    @OneToMany(mappedBy = "patient")
//    @JsonManagedReference
//    List<EnrolledCourse> enrolledCourses;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    List<Quiz> quizScores;


    public PatientDto toDto(){
        return new PatientDto(user.getEmail(),user.getUserId(), firstName,middleName,lastName,age,mobileNo,gender,image);
    }
}
