package com.TranquilMind.repository;

import com.TranquilMind.model.Course;
import com.TranquilMind.model.EnrolledCourse;
import com.TranquilMind.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollCourseRepository extends JpaRepository<EnrolledCourse,Long> {

    EnrolledCourse findByCourseAndPatient(Course course, Patient patient);

    List<EnrolledCourse> findByPatient(Patient patient);

}
