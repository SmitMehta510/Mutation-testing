package com.TranquilMind.controller;

import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.PatientDto;
import com.TranquilMind.dto.PatientRegisterDto;
import com.TranquilMind.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegisterDto patientRegisterDto){
        return patientService.createPatient(patientRegisterDto);
    }

    @GetMapping("/patientbyid/{id}")
    public PatientDto patientById(@PathVariable Long id){
        return patientService.getPatientDtoByUserId(id);
    }

    @PutMapping("/updatepatient/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientRegisterDto newPatientDetails){
        return new ResponseEntity<>(patientService.updatePatient(id, newPatientDetails), HttpStatus.OK);
    }

    @DeleteMapping("/deletepatient/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable String id){
        System.out.println("Id received is " + id );
        return new ResponseEntity<>(patientService.deletePatient(Long.parseLong(id)),HttpStatus.GONE);
    }

    @GetMapping("/get-quiz-score/{userId}")
    public ResponseEntity<?> getQuizScore(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.getQuizzes(userId), HttpStatus.OK);
    }

    @GetMapping("/my-posts/{userId}")
    public ResponseEntity<?> getMyPosts(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.getPosts(userId), HttpStatus.OK);
    }

    @GetMapping("/my-questions/{userId}")
    public ResponseEntity<?> getMyQuestions(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.getQuestions(userId), HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(patientService.updatePassword(passwordDto), HttpStatus.OK);
    }

    @PostMapping("/{patientId}/enroll-course/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long patientId, @PathVariable Long courseId){
        return new ResponseEntity<>(patientService.enrollCourse(patientId, courseId), HttpStatus.OK);
    }

    @PutMapping("/{patientId}/mark-complete/{courseId}")
    public ResponseEntity<?> markComplete(@PathVariable Long patientId, @PathVariable Long courseId){
        return new ResponseEntity<>(patientService.markComplete(patientId, courseId), HttpStatus.OK);
    }

    @GetMapping("/enrolled-courses/{userId}")
    public ResponseEntity<?> getEnrolledCourses(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.enrollCourses(userId), HttpStatus.OK);
    }
}   
