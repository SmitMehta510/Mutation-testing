package com.TranquilMind.controller;


import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.model.Question;
import com.TranquilMind.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/approved-questions")
    public ResponseEntity<?> getApprovedQuestions() {
        return new ResponseEntity<>(questionService.getApprovedQuestions(), HttpStatus.OK);
    }

    @GetMapping("/question-data")
    public ResponseEntity<?> getQuestionData() {
        return new ResponseEntity<>(questionService.questionData(), HttpStatus.OK);
    }

    @PostMapping("/add-question")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionDto questionDto) {
        return new ResponseEntity<>(questionService.addQuestion(questionDto), HttpStatus.OK);
    }
}
