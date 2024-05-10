package com.TranquilMind.repository;

import com.TranquilMind.model.Course;
import com.TranquilMind.model.EnrolledCourse;
import com.TranquilMind.model.Patient;
import com.TranquilMind.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollCourseRepository extends JpaRepository<EnrolledCourse,Long> {

    EnrolledCourse findByCourseAndUser(Course course, User user);

    List<EnrolledCourse> findByUser(User user);

}
