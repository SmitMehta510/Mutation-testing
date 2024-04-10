package com.TranquilMind.controller;

import com.TranquilMind.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    @Autowired
    private ModeratorService moderatorService;

    @GetMapping("/flagged-posts")
    public ResponseEntity<?> getFlaggedPosts() {
        return new ResponseEntity<>(moderatorService.getFlaggedPosts(),HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,@RequestBody Boolean unflag) {
        return new ResponseEntity<>(moderatorService.updatePost(id,unflag),HttpStatus.OK);
    }

    @GetMapping("/unapproved-answers")
    public ResponseEntity<?> getUnapprovedAnswers() {
        return new ResponseEntity<>(moderatorService.getAnsweredQuestions(),HttpStatus.OK);
    }

    @PutMapping("/approve-answer/{questionId}")
    public ResponseEntity<?> approveAnswer(@PathVariable Long questionId) {
        return new ResponseEntity<>(moderatorService.approveAnswer(questionId),HttpStatus.OK);
    }

//    @PostMapping("/di")


}
