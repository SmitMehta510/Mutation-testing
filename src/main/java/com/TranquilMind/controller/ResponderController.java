package com.TranquilMind.controller;

import com.TranquilMind.service.ResponderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/response")
public class ResponderController {

    @Autowired
    private ResponderService responderService;

    @GetMapping("get-answered-questions/{id}")
    public ResponseEntity<?> getAnsweredQuestions(@PathVariable Long id){
        return new ResponseEntity<>(responderService.getAnsweredPostsByResponder(id), HttpStatus.OK);
    }
}
