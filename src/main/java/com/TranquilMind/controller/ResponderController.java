package com.TranquilMind.controller;

import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.dto.QuestionDto;
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
@RequestMapping("/responder")
@Tag(name = "Responder", description = "API regarding responder functionalities")
public class ResponderController {

    @Autowired
    private ResponderService responderService;

    /**
     * Retrieve answered questions by responder ID.
     *
     * @param id The ID of the responder.
     * @return Answered questions by the responder.
     */
    @Operation(summary = "Get answered questions by responder ID", description = "Retrieve answered questions by responder ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("get-answered-questions/{id}")
    public ResponseEntity<?> getAnsweredQuestions(@PathVariable Long id) {
        return new ResponseEntity<>(responderService.getAnsweredQuestionsByResponder(id), HttpStatus.OK);
    }

    /**
     * Retrieve unanswered questions.
     *
     * @return Unanswered questions.
     */
    @Operation(summary = "Get unanswered questions", description = "Retrieve unanswered questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/get-unanswered-questions")
    public ResponseEntity<?> getUnansweredQuestions() {
        return new ResponseEntity<>(responderService.getUnansweredQuestions(), HttpStatus.OK);
    }

    /**
     * Add an answer to a question.
     *
     * @param questionId The ID of the question.
     * @param questionDto The answer details.
     * @return HttpStatus.OK if successful, HttpStatus.BAD_REQUEST otherwise.
     */
    @Operation(summary = "Add an answer to a question", description = "Add an answer to a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/add-answer/{questionId}")
    public ResponseEntity<?> addAnswer(@PathVariable Long questionId,
                                       @RequestBody QuestionDto questionDto) {

        if(responderService.addAnswer(questionDto, questionId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieve a responder by user ID.
     *
     * @param userId The ID of the user.
     * @return The responder details.
     */
    @Operation(summary = "Get responder by user ID", description = "Retrieve a responder by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Responder not found")
    })
    @GetMapping("/getbyid/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(responderService.getResponderByUserId(userId),HttpStatus.OK);
    }

    /**
     * Update responder password.
     *
     * @param passwordDto The new password.
     * @return HttpStatus.OK if successful, HttpStatus.BAD_REQUEST otherwise.
     */
    @Operation(summary = "Update responder password", description = "Update responder password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(responderService.updatePassword(passwordDto),HttpStatus.OK);
    }
}
