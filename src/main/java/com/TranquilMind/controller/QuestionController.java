package com.TranquilMind.controller;

import com.TranquilMind.model.Question;
import com.TranquilMind.model.QuizType;
import com.TranquilMind.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("get-questions/{quizName}")
    public ResponseEntity<?> getQuestionByQuizType(@PathVariable String quizName) {
        return new ResponseEntity<>(questionService.getAllQuestions(quizName), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.addNewQuestion(question), HttpStatus.OK);
    }

}
