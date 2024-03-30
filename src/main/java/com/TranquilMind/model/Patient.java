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

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference
    List<Appointment> appointmentList;

    @OneToMany(mappedBy = "patient")
    List<EnrolledCourse> enrolledCourses;

    @OneToMany(mappedBy = "patient")
    List<Quiz> quizScores;


//    public DoctorDto toDto(Doctor doctor){
//        return new DoctorDto(doctor.getUser().getUserId(),doctor.getUser().getEmail(),
//                doctor.getFirstName(), doctor.getMiddleName(), doctor.getLastName(), doctor.getAge(),doctor.getGender(),
//                doctor.getMobileNo(), doctor.getLicenceNo(), doctor.getDescription(), doctor.getConsultationFee(),
//                doctor.getExperience(), doctor.getIsSenior(),doctor.getIsDisabled());
//    }

    public PatientDto toDto(Patient patient){
       return new PatientDto(patient.getUser().getEmail(),patient.getUser().getUserId(), patient.getFirstName(),
               patient.getMiddleName(), patient.getLastName(), patient.getAge(), patient.getMobileNo(),
               patient.getGender());
    }
}
