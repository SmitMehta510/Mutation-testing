package com.TranquilMind.controller;

import com.TranquilMind.model.QuizQuestion;
import com.TranquilMind.service.QuizQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/quiz-question")
public class QuizQuestionController {

    @Autowired
    private QuizQuestionService quizQuestionService;

    @GetMapping("get-questions/{quizName}")
    public ResponseEntity<?> getQuestionByQuizType(@PathVariable String quizName) {

        List<QuizQuestion> questions = quizQuestionService.getAllQuizQuestions(quizName);

        if(questions == null ||questions.isEmpty() ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(questions, HttpStatus.OK);
        }

//        return new ResponseEntity<>(quizQuestionService.getAllQuizQuestions(quizName), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newQuestion(@RequestBody QuizQuestion question) {
        return new ResponseEntity<>(quizQuestionService.addNewQuizQuestion(question), HttpStatus.OK);
    }

}
