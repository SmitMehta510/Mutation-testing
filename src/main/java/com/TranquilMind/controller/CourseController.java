package com.TranquilMind.controller;

import com.TranquilMind.dto.CourseDto;
import com.TranquilMind.model.Task;
import com.TranquilMind.service.CourseService;
import com.TranquilMind.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/admin/add-course")
    public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto){
        return new ResponseEntity<>(courseService.addCourse(courseDto), HttpStatus.CREATED);
    }

    @PutMapping("/admin/update-course")
    public ResponseEntity<?> updateCourse(CourseDto courseDto){
        return new ResponseEntity<>(courseService.updateCourse(courseDto),HttpStatus.OK);
    }

    @PostMapping("/admin/add-task")
    public ResponseEntity<?> addTask(@RequestBody Task task){
        return new ResponseEntity<>(taskService.addTask(task),HttpStatus.OK);
    }

    @PutMapping("/admin/update-task")
    public ResponseEntity<?> editTask(@RequestBody Task task){
        return new ResponseEntity<>(taskService.editTask(task),HttpStatus.OK);
    }

    @GetMapping("/get-courses")
    public ResponseEntity<?> getCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(),HttpStatus.OK);
    }

    @GetMapping("/get-course/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }

}
