package com.TranquilMind.controller;

import com.TranquilMind.dto.QuizDto;
import com.TranquilMind.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/new")
    public ResponseEntity<?> addNewQuizScore(@RequestBody QuizDto quizDto){
        return new ResponseEntity<>(quizService.newQuiz(quizDto),HttpStatus.OK);
    }

    @GetMapping("/quiz-types")
    public ResponseEntity<?> getQuizTypes(){
        return new ResponseEntity<>(quizService.getQuizTypes(),HttpStatus.OK);
    }
}
