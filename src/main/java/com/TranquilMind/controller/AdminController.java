package com.TranquilMind.controller;

import com.TranquilMind.model.Moderator;
import com.TranquilMind.model.Responder;
import com.TranquilMind.service.DoctorService;
import com.TranquilMind.service.ModeratorService;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.ResponderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
@Tag(name = "Admin", description = "API regarding admin functionalities")
public class AdminController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ModeratorService moderatorService;

    @Autowired
    private ResponderService responderService;


    /**
     * Retrieve all patients.
     *
     * @return A list of all patients.
     */
    @Operation(summary = "Get all patients", description = "Retrieve all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/patients")
    public ResponseEntity<?> getAllPatients(){
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    /**
     * Retrieve all doctors.
     *
     * @return A list of all doctors.
     */
    @Operation(summary = "Get all doctors", description = "Retrieve all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/doctors")
    public ResponseEntity<?> getAllDoctors(){
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    /**
     * Retrieve all moderators.
     *
     * @return A list of all moderators.
     */
    @Operation(summary = "Get all moderators", description = "Retrieve all moderators")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/moderators")
    public ResponseEntity<?> getAllModerators(){
        return new ResponseEntity<>(moderatorService.getAllModerators(), HttpStatus.OK);
    }

    /**
     * Retrieve all responders.
     *
     * @return A list of all responders.
     */
    @Operation(summary = "Get all responders", description = "Retrieve all responders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/responders")
    public ResponseEntity<?> getAllResponders(){
        return new ResponseEntity<>(responderService.getAllResponders(), HttpStatus.OK);
    }

    /**
     * Retrieve all disabled doctors.
     *
     * @return A list of all disabled doctors.
     */
    @Operation(summary = "Get all disabled doctors", description = "Retrieve all disabled doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/disabled-doctors")
    public ResponseEntity<?> getDisabledDoctors(){
        return new ResponseEntity<>(doctorService.getAllDisabledDoctors(),HttpStatus.OK);
    }

    /**
     * Approve or disapprove a doctor.
     *
     * @param doctorId The ID of the doctor.
     * @param approve  Approval status (true/false).
     * @return A message confirming approval/disapproval.
     */
    @Operation(summary = "Approve or disapprove a doctor", description = "Approve or disapprove a doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor approval status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @PutMapping("/approve-doctor/{doctorId}")
    public ResponseEntity<?> approveDoctor(@PathVariable Long doctorId, @RequestBody Boolean approve){
        return new ResponseEntity<>(doctorService.approveDoctor(doctorId, approve), HttpStatus.OK);
    }

    /**
     * Add a new responder.
     *
     * @param responder The details of the new responder.
     * @return The newly added responder.
     */
    @Operation(summary = "Add a new responder", description = "Add a new responder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Responder added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add-responder")
    public ResponseEntity<?> addResponder(@RequestBody Responder responder){
        return new ResponseEntity<>(responderService.addResponder(responder),HttpStatus.OK);
    }

    /**
     * Add a new moderator.
     *
     * @param moderator The details of the new moderator.
     * @return The newly added moderator.
     */
    @Operation(summary = "Add a new moderator", description = "Add a new moderator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Moderator added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add-moderator")
    public ResponseEntity<?> addModerator(@RequestBody Moderator moderator){
        return new ResponseEntity<>(moderatorService.addModerator(moderator),HttpStatus.OK);
    }
}
