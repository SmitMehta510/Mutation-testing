package com.TranquilMind.controller;

import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.PatientDto;
import com.TranquilMind.dto.PatientRegisterDto;
import com.TranquilMind.model.Patient;
import com.TranquilMind.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patient")
@Tag(name = "Patient", description = "API regarding patient functionalities")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Operation(summary = "Add a new patient to the application", description = "Add a new patient to the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/xml", schema = @Schema(implementation = Patient.class)), @Content(mediaType = "application/json", schema = @Schema(implementation = Patient.class)) }),
            @ApiResponse(responseCode = "405", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegisterDto patientRegisterDto){
        return patientService.createPatient(patientRegisterDto);
    }

    /**
     * Get a patient by their ID.
     *
     * @param id The ID of the patient.
     * @return The patient's information.
     */
    @Operation(summary = "Get a patient by ID", description = "Retrieve information about a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/patientbyid/{id}")
    public PatientDto patientById(@PathVariable Long id){
        return patientService.getPatientDtoByUserId(id);
    }

    /**
     * Update details of an existing patient.
     *
     * @param id The ID of the patient.
     * @param newPatientDetails The updated details of the patient.
     * @return The updated patient information.
     */
    @Operation(summary = "Update patient details", description = "Update details of an existing patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PatientDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @PutMapping("/updatepatient/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientRegisterDto newPatientDetails){
        return new ResponseEntity<>(patientService.updatePatient(id, newPatientDetails), HttpStatus.OK);
    }

    /**
     * Delete a patient by their ID.
     *
     * @param id The ID of the patient.
     * @return A message confirming the deletion.
     */
    @Operation(summary = "Delete a patient by ID", description = "Delete a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @DeleteMapping("/deletepatient/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable String id){
        System.out.println("Id received is " + id );
        return new ResponseEntity<>(patientService.deletePatient(Long.parseLong(id)),HttpStatus.GONE);
    }

    /**
     * Get the quiz scores of a patient.
     *
     * @param userId The ID of the patient.
     * @return The quiz scores of the patient.
     */
    @Operation(summary = "Get quiz scores of a patient", description = "Retrieve quiz scores of a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/get-quiz-score/{userId}")
    public ResponseEntity<?> getQuizScore(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.getQuizzes(userId), HttpStatus.OK);
    }

    /**
     * Get posts associated with a patient.
     *
     * @param userId The ID of the patient.
     * @return The posts associated with the patient.
     */
    @Operation(summary = "Get posts associated with a patient", description = "Retrieve posts associated with a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/my-posts/{userId}")
    public ResponseEntity<?> getMyPosts(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.getPosts(userId), HttpStatus.OK);
    }

    /**
     * Get questions asked by a patient.
     *
     * @param userId The ID of the patient.
     * @return The questions asked by the patient.
     */
    @Operation(summary = "Get questions asked by a patient", description = "Retrieve questions asked by a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/my-questions/{userId}")
    public ResponseEntity<?> getMyQuestions(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.getQuestions(userId), HttpStatus.OK);
    }

    /**
     * Update password of a patient.
     *
     * @param passwordDto The password details.
     * @return A message confirming password update.
     */
    @Operation(summary = "Update password of a patient", description = "Update password of a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(patientService.updatePassword(passwordDto), HttpStatus.OK);
    }

    /**
     * Enroll a patient into a course.
     *
     * @param patientId The ID of the patient.
     * @param courseId The ID of the course.
     * @return A message confirming enrollment.
     */
    @Operation(summary = "Enroll a patient into a course", description = "Enroll a patient into a course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enrollment successful"),
            @ApiResponse(responseCode = "404", description = "Patient or course not found")
    })
    @PostMapping("/{patientId}/enroll-course/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Long patientId, @PathVariable Long courseId){
        return new ResponseEntity<>(patientService.enrollCourse(patientId, courseId), HttpStatus.OK);
    }

    /**
     * Mark a course as complete for a patient.
     *
     * @param patientId The ID of the patient.
     * @param courseId The ID of the course.
     * @return A message confirming completion.
     */
    @Operation(summary = "Mark a course as complete for a patient", description = "Mark a course as complete for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course marked as complete"),
            @ApiResponse(responseCode = "404", description = "Patient or course not found")
    })
    @PutMapping("/{patientId}/mark-complete/{courseId}")
    public ResponseEntity<?> markComplete(@PathVariable Long patientId, @PathVariable Long courseId){
        return new ResponseEntity<>(patientService.markComplete(patientId, courseId), HttpStatus.OK);
    }

    /**
     * Get courses enrolled by a patient.
     *
     * @param userId The ID of the patient.
     * @return The courses enrolled by the patient.
     */
    @Operation(summary = "Get courses enrolled by a patient", description = "Retrieve courses enrolled by a patient by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/enrolled-courses/{userId}")
    public ResponseEntity<?> getEnrolledCourses(@PathVariable Long userId){
        return new ResponseEntity<>(patientService.enrollCourses(userId), HttpStatus.OK);
    }

    /**
     * Check if a task is completed by a patient for a specific course.
     *
     * @param patientId The ID of the patient.
     * @param courseId The ID of the course.
     * @return A message indicating task completion status.
     */
    @Operation(summary = "Check if a task is completed by a patient for a specific course", description = "Check if a task is completed by a patient for a specific course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Patient or course not found")
    })
    @GetMapping("/{patientId}/task-complete/{courseId}")
    public ResponseEntity<?> taskCompleted(@PathVariable Long patientId, @PathVariable Long courseId){
        return new ResponseEntity<>(patientService.taskComplete(patientId, courseId), HttpStatus.OK);
    }
}   
