package com.TranquilMind.model;

import com.TranquilMind.dto.AppointmentListDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonBackReference
    Patient patient;

    LocalDate date;

    LocalTime startTime;

    LocalTime endTime;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String description;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    String remarks;

    public static AppointmentListDto toListDto(Appointment appointment){
        return  new AppointmentListDto(appointment.appointmentId, appointment.patient.toDto(),appointment.doctor.toDto(),
                appointment.date,appointment.startTime,appointment.endTime,appointment.description,appointment.remarks);
    }

}
