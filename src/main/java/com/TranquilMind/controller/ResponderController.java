package com.TranquilMind.controller;

import com.TranquilMind.dto.QuestionDto;
import com.TranquilMind.service.ResponderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/responder")
public class ResponderController {

    @Autowired
    private ResponderService responderService;

    @GetMapping("get-answered-questions/{id}")
    public ResponseEntity<?> getAnsweredQuestions(@PathVariable Long id) {
        return new ResponseEntity<>(responderService.getAnsweredQuestionsByResponder(id), HttpStatus.OK);
    }

    @GetMapping("/get-unanswered-questions")
    public ResponseEntity<?> getUnansweredQuestions() {
        return new ResponseEntity<>(responderService.getUnansweredQuestions(), HttpStatus.OK);
    }

    @PutMapping("/add-answer/{questionId}")
    public ResponseEntity<?> addAnswer(@PathVariable Long questionId,
        @RequestBody QuestionDto questionDto, @RequestParam(name = "answeredBy") Long answeredById) {

        if(responderService.addAnswer(questionDto, questionId, answeredById)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getbyid/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(responderService.getResponderByUserId(userId),HttpStatus.OK);
    }
}
