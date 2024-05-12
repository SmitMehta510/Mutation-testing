package com.TranquilMind.controller;

import com.TranquilMind.dto.QuizDto;
import com.TranquilMind.model.Patient;
import com.TranquilMind.service.PatientService;
import com.TranquilMind.service.QuizService;
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
@RequestMapping("/quiz")
@Tag(name = "Quiz", description = "API for Quiz functionalities")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private PatientService patientService;

    /**
     * Add a new quiz score.
     *
     * @param quizDto The details of the new quiz score.
     * @return The newly added quiz score.
     */
    @Operation(summary = "Add a new quiz score", description = "Add a new quiz score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz score added successfully", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("/new")
    public ResponseEntity<?> addNewQuizScore(@RequestBody QuizDto quizDto){
        return new ResponseEntity<>(quizService.newQuiz(quizDto),HttpStatus.OK);
    }

    /**
     * Retrieve available quiz types.
     *
     * @return List of available quiz types.
     */
    @Operation(summary = "Get quiz types", description = "Retrieve available quiz types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/quiz-types")
    public ResponseEntity<?> getQuizTypes(){
        return new ResponseEntity<>(quizService.getQuizTypes(),HttpStatus.OK);
    }

    /**
     * Retrieve quiz score for a user.
     *
     * @param userId The ID of the user.
     * @return Quiz score for the user.
     */
    @Operation(summary = "Get quiz score", description = "Retrieve quiz score for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Patient not found")
    })
    @GetMapping("/quiz-score/{userId}")
    public ResponseEntity<?> getQuizScore(@PathVariable Long userId){
        Patient patient = patientService.getPatientByUserId(userId);
        if (patient != null) {
            return new ResponseEntity<>(quizService.getQuizScoresForPatient(patient),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
