package com.TranquilMind.service.serviceImpl;

import com.TranquilMind.dto.CourseDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Course;
import com.TranquilMind.model.Task;
import com.TranquilMind.repository.CourseRepository;
import com.TranquilMind.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Course course =  courseRepository.findById(id).orElseThrow(
                () ->new ResourceNotFoundException("Course not found with id "+id));

        return course;
//        course.getTasks().sort();
    }

    @Override
    public Course updateCourse(CourseDto courseDto) {
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

        List<Task> taskList = new ArrayList<>();
        courseDto.getTasks().forEach(taskDto -> {
            Task task1 = new Task();
            task1.setDescription(taskDto.getDescription());
            task1.setCourse(course);
            task1.setTaskNo(taskDto.getTaskNo());
            task1.setWeekNo(taskDto.getWeekNo());
            task1.setLink(taskDto.getLink());
            taskList.add(task1);
        });

        course.setTasks(taskList);
        return courseRepository.save(course);
    }
}
