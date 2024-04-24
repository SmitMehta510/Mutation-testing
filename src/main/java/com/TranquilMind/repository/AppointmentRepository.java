package com.TranquilMind.repository;

import com.TranquilMind.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @Query("SELECT a FROM Appointment a WHERE a.date= :date")
    List<Appointment> fetchByDate(@Param("date") LocalDate date);

    @Query("select count(distinct a.patient.patientId) from Appointment a where a.doctor.doctorId= :doctorId")
    Integer getPatientDataByDoctorId(@Param("doctorId") Long doctorId);

    @Query("select count(*) from Appointment a where a.doctor.doctorId= :doctorId")
    Integer totalAppointmentsByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT COUNT(*) FROM Appointment a WHERE a.doctor.doctorId = :doctorId AND (a.date > :date OR (a.date = :date AND a.startTime > :time))")
    Integer totalPendingAppointmentsByDoctorId(@Param("doctorId") Long doctorId, @Param("date") LocalDate date, @Param("time") LocalTime time);

    @Query("select distinct(a.patient.patientId) from Appointment a where a.doctor.doctorId = :doctorId")
    List<Long> findDistinctPatientByDoctorId(@Param("doctorId") Long doctorId);

//    Integer countAllByDoctorAndDateGreaterThanEqualAndStartTimeGreaterThan(Doctor doctor, LocalDate date, LocalTime startTime);
}
