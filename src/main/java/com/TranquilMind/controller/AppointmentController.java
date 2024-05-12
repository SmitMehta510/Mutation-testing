package com.TranquilMind.controller;

import com.TranquilMind.dto.AppointmentDto;
import com.TranquilMind.dto.AppointmentListDto;
import com.TranquilMind.model.Appointment;
import com.TranquilMind.service.AppointmentService;
import com.TranquilMind.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/appointment")
@Tag(name = "Appointment", description = "API for appointment functionalities")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    /**
     * Retrieve appointments for a patient.
     *
     * @param id The ID of the patient.
     * @return Appointments for the patient.
     */
    @Operation(summary = "Get patient appointments", description = "Retrieve appointments for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/patient-appointments/{id}")
    public ResponseEntity<?> getPatientAppointments(@PathVariable Long id){
        return new ResponseEntity<>(appointmentService.getAppointmentsForPatient(id),HttpStatus.OK);
    }

    /**
     * Retrieve appointments for a doctor.
     *
     * @param id The ID of the doctor.
     * @return Appointments for the doctor.
     */
    @Operation(summary = "Get doctor appointments", description = "Retrieve appointments for a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/doctor-appointments/{id}")
    public ResponseEntity<?> getDoctorAppointments(@PathVariable Long id){
        List<AppointmentListDto> appointments = appointmentService.getAppointmentsForDoctor(id);

        if(appointments!=null){
            return new ResponseEntity<>(appointments,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieve appointments for a doctor on a specific date.
     *
     * @param id   The ID of the doctor.
     * @param date The date for appointments.
     * @return Appointments for the doctor on the specific date.
     */
    @Operation(summary = "Get doctor appointments by date", description = "Retrieve appointments for a doctor on a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/doctor-appointments/{id}/date/{date}")
    public ResponseEntity<?> getDoctorAppointmentsByDate(@PathVariable Long id, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return new ResponseEntity<>(appointmentService.getAppointmentsForDoctorByDate(id,date),HttpStatus.OK);
    }

    /**
     * Retrieve the list of doctors.
     *
     * @return List of doctors.
     */
    @Operation(summary = "Get doctors list", description = "Retrieve the list of doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/doctors")
    public ResponseEntity<?> getDoctorsList(){
        return new ResponseEntity<>(doctorService.getAllActiveDoctors(),HttpStatus.OK);
    }

    /**
     * Schedule a new appointment.
     *
     * @param appointmentDto The details of the new appointment.
     * @return The newly scheduled appointment.
     */
    @Operation(summary = "Schedule a new appointment", description = "Schedule a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment scheduled successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/new-appointment")
    public ResponseEntity<?> newAppointment(@RequestBody AppointmentDto appointmentDto){
        Appointment appointment = appointmentService.newAppointment(appointmentDto);

        if(appointment!=null){
            return new ResponseEntity<>(appointment,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    /**
//     * Cancel an appointment.
//     *
//     * @param id The ID of the appointment to be cancelled.
//     * @return HttpStatus.GONE if successful.
//     */
//    @Operation(summary = "Cancel an appointment", description = "Cancel an appointment")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "410", description = "Appointment cancelled successfully")
//    })
//    @DeleteMapping("/cancel/{id}")
//    public ResponseEntity<?> deleteAppointment(@PathVariable Long id){
//        return new ResponseEntity<>(HttpStatus.GONE);
//    }

}
