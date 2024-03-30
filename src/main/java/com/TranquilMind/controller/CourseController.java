package com.TranquilMind.controller;

import com.TranquilMind.dto.CourseDto;
import com.TranquilMind.service.CourseService;
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

    @PostMapping("/admin/add")
    public ResponseEntity<?> addCourse(@RequestBody CourseDto courseDto){
        return new ResponseEntity<>(courseService.addCourse(courseDto), HttpStatus.CREATED);
    }

    @GetMapping("/get-courses")
    public ResponseEntity<?> getCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(),HttpStatus.OK);
    }

    @GetMapping("/get-course/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }

//    @GetMapping("/admin/test")
//    public String testadmin(){
//        return "admin";
//    }
//
//    @GetMapping("/patient/test")
//    public String testPatient(){
//        return "patient";
//    }
//
//    @GetMapping("/test")
//    public String test(){
//        return "test";
//    }
}
