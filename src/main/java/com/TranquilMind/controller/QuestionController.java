package com.TranquilMind.controller;


import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.model.Question;
import com.TranquilMind.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/question")
@Tag(name = "Question", description = "API regarding Questions in the forum")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * Retrieve approved questions.
     *
     * @return List of approved questions.
     */
    @Operation(summary = "Get approved questions", description = "Retrieve approved questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/approved-questions")
    public ResponseEntity<?> getApprovedQuestions() {
        return new ResponseEntity<>(questionService.getApprovedQuestions(), HttpStatus.OK);
    }

    /**
     * Retrieve question data.
     *
     * @return Question data.
     */
    @Operation(summary = "Get question data", description = "Retrieve question data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/question-data")
    public ResponseEntity<?> getQuestionData() {
        return new ResponseEntity<>(questionService.questionData(), HttpStatus.OK);
    }

    /**
     * Add a new question.
     *
     * @param questionDto The details of the new question.
     * @return The newly added question.
     */
    @Operation(summary = "Add a new question", description = "Add a new question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question added successfully", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("/add-question")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionDto questionDto) {
        return new ResponseEntity<>(questionService.addQuestion(questionDto), HttpStatus.OK);
    }
}
