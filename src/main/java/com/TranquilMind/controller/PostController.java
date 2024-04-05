package com.TranquilMind.controller;

import com.TranquilMind.dto.PostDto;
import com.TranquilMind.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/get-posts")
    public ResponseEntity<?> getPosts(){
        return new ResponseEntity<>(postService.getAllPostsByTime(), HttpStatus.OK);
    }

    @PostMapping("/add-post")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        return  new ResponseEntity<>(postService.addPost(postDto),HttpStatus.OK);
    }

    public ResponseEntity<?> editPost(@PathVariable Long id, @RequestBody PostDto postDto){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
