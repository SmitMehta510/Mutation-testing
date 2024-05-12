package com.TranquilMind.controller;

import com.TranquilMind.dto.DoctorRegisterDto;
import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.exception.ResourceNotFoundException;
import com.TranquilMind.model.Doctor;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.utilities.AppLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/doctor")
@Tag(name = "Doctor", description = "API regarding doctor functionalities")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    Logger logger = AppLogger.getLogger();

    /**
     * Register a new doctor.
     *
     * @param doctorRegisterDto The details of the new doctor.
     * @return The newly registered doctor.
     */
    @Operation(summary = "Register a new doctor", description = "Register a new doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor registered successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@RequestBody DoctorRegisterDto doctorRegisterDto) {
        return doctorService.createDoctor(doctorRegisterDto);
    }

    /**
     * Get details of a doctor by their user ID.
     *
     * @param id The ID of the doctor.
     * @return The details of the doctor.
     */
    @Operation(summary = "Get details of a doctor by ID", description = "Get details of a doctor by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/doctorbyid/{id}")
    public ResponseEntity<?> doctorByUserId(@PathVariable Long id) {
        Doctor doctor;
        try {
            doctor = doctorService.getDoctorByUserId(id);
        } catch (ResourceNotFoundException e) {
            logger.error(e.toString(),e.getMessage(),e);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(doctor);
    }

    /**
     * Update details of an existing doctor.
     *
     * @param id      The ID of the doctor.
     * @param doctor  The updated details of the doctor.
     * @return The updated doctor information.
     */
    @Operation(summary = "Update details of an existing doctor", description = "Update details of an existing doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor details updated successfully", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Doctor.class))}),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @PutMapping("/update-doctor/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Long id, Doctor doctor){
        return new ResponseEntity<>(doctorService.updateDoctor(id, doctor), HttpStatus.OK);
    }

    /**
     * Get statistics for a doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return The statistics for the doctor.
     */
    @Operation(summary = "Get statistics for a doctor", description = "Get statistics for a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/get-stats/{doctorId}")
    public ResponseEntity<?> getStatsForDoctor(@PathVariable Long doctorId){
        return new ResponseEntity<>(doctorService.getStatsForDoctor(doctorId),HttpStatus.OK);
    }

    /**
     * Update password of a doctor.
     *
     * @param passwordDto The password details.
     * @return A message confirming password update.
     */
    @Operation(summary = "Update password of a doctor", description = "Update password of a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(doctorService.updatePassword(passwordDto),HttpStatus.OK);
    }

    /**
     * Check if a doctor is a senior doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return A boolean indicating if the doctor is a senior doctor.
     */
    @Operation(summary = "Check if a doctor is a senior doctor", description = "Check if a doctor is a senior doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/is-senior/{doctorId}")
    public ResponseEntity<?> isSenior(@PathVariable Long doctorId){
        return new ResponseEntity<>(doctorService.getIsSenior(doctorId),HttpStatus.OK);
    }

    /**
     * Get the count of distinct patients associated with a doctor.
     *
     * @param doctorId The ID of the doctor.
     * @return The count of distinct patients associated with the doctor.
     */
    @Operation(summary = "Get the count of distinct patients associated with a doctor", description = "Get the count of distinct patients associated with a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/distinct-patient/{doctorId}")
    public ResponseEntity<?> distinctPatient(@PathVariable Long doctorId){
        return new ResponseEntity<>(doctorService.distinctPatientByDoctorId(doctorId),HttpStatus.OK);
    }
}