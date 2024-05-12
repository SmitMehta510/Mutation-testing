package com.TranquilMind.controller;

import com.TranquilMind.dto.QuizQuestionDto;
import com.TranquilMind.model.QuizQuestion;
import com.TranquilMind.service.QuizQuestionService;
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
@RequestMapping("/quiz-question")
@Tag(name = "Quiz-Questions", description = "API for functionalities regarding Quiz Questions")
public class QuizQuestionController {

    @Autowired
    private QuizQuestionService quizQuestionService;

    /**
     * Retrieve questions by quiz type.
     *
     * @param quizName The name of the quiz.
     * @return List of questions for the specified quiz.
     */
    @Operation(summary = "Get questions by quiz type", description = "Retrieve questions by quiz type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No questions found for the specified quiz type")
    })
    @GetMapping("get-questions/{quizName}")
    public ResponseEntity<?> getQuestionByQuizType(@PathVariable String quizName) {

        List<QuizQuestion> questions = quizQuestionService.getAllQuizQuestions(quizName);

        if(questions == null || questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }
    }

    /**
     * Add a new quiz question.
     *
     * @param quizQuestionDto The details of the new quiz question.
     * @return The newly added quiz question.
     */
    @Operation(summary = "Add a new quiz question", description = "Add a new quiz question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quiz question added successfully", content = {@Content(mediaType = "application/json")})
    })
    @PostMapping("/new")
    public ResponseEntity<?> newQuestion(@RequestBody QuizQuestionDto quizQuestionDto) {
        return new ResponseEntity<>(quizQuestionService.addNewQuizQuestion(quizQuestionDto), HttpStatus.OK);
    }

}
