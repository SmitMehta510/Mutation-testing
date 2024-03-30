package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.CourseDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Course;
import com.TranquilMind.repository.CourseRepository;
import com.TranquilMind.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Course not found with id "+id));
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        return null;
    }

    @Override
    public Course addCourse(CourseDto courseDto) {
        Course course = new Course();

        course.setCourseName(courseDto.getCourseName());
        course.setCategory(courseDto.getCategory());
        course.setPrice(courseDto.getPrice());
        course.setDescription(courseDto.getDescription());
        course.setTotalTask(courseDto.getTotalTask());
        course.setTasks(courseDto.getTasks());
        return courseRepository.save(course);
    }
}
