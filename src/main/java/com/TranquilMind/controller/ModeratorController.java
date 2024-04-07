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

    @GetMapping("/unapproved-questions")
    public ResponseEntity<?> getUnapprovedQuestions() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/flagged-posts")
    public ResponseEntity<?> getFlaggedPosts() {
        return new ResponseEntity<>(moderatorService.getFlaggedPosts(),HttpStatus.OK);
    }

    @PutMapping("/unflag/{id}")
    public ResponseEntity<?> unflagPost(@PathVariable Long id) {
        return new ResponseEntity<>(moderatorService.unflagPost(id),HttpStatus.OK);
    }
}
