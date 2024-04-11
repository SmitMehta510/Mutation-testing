package com.TranquilMind.service;

import com.TranquilMind.dto.CourseDto;
import com.TranquilMind.model.Course;
import java.util.List;
public interface CourseService {

    List<Course> getAllCourses();

    CourseDto getCourseById(Long id);

    Course updateCourse(CourseDto courseDto);

    Course addCourse(CourseDto courseDto);

}
