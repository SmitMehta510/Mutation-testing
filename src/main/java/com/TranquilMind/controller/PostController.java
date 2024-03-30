package com.TranquilMind.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {


    @GetMapping("/posts")
    public ResponseEntity<?> getPostsByTime(){



        return null;
    }


}
