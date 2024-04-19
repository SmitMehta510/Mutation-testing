package com.TranquilMind.controller;

import com.TranquilMind.dto.PasswordDto;
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

    @PutMapping("/unflag/{id}")
    public ResponseEntity<?> unflagPost(@PathVariable Long id,@RequestBody Boolean unflag) {
        return new ResponseEntity<>(moderatorService.unflagPost(id,unflag),HttpStatus.OK);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disablePost(@PathVariable Long id,@RequestBody Boolean disable) {
        return new ResponseEntity<>(moderatorService.disablePost(id,disable),HttpStatus.OK);
    }

    @GetMapping("/unapproved-answers")
    public ResponseEntity<?> getUnapprovedAnswers() {
        return new ResponseEntity<>(moderatorService.getAnsweredQuestions(),HttpStatus.OK);
    }

    @PutMapping("/approve-answer/{questionId}")
    public ResponseEntity<?> approveAnswer(@PathVariable Long questionId) {
        return new ResponseEntity<>(moderatorService.approveAnswer(questionId),HttpStatus.OK);
    }

    @GetMapping("/getbyid/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(moderatorService.getModeratorByUserId(userId),HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(moderatorService.updatePassword(passwordDto),HttpStatus.OK);
    }
}
