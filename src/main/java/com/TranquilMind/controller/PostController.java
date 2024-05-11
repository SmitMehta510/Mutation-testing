package com.TranquilMind.controller;

import com.TranquilMind.dto.CommentDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Comment;
import com.TranquilMind.model.Post;
import com.TranquilMind.service.CommentService;
import com.TranquilMind.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/get-posts")
    public ResponseEntity<?> getPosts(){
        return new ResponseEntity<>(postService.getAllPostsByTime(), HttpStatus.OK);
    }

    @PostMapping("/add-post")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        PostDto post = postService.addPost(postDto);

        if(post != null){
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-post/{postId}/user/{userId}")
    public ResponseEntity<?> editPost(@PathVariable Long postId, @PathVariable Long userId,
                                      @RequestBody PostDto postdto){
        PostDto dto = postService.editPost(postdto,userId,postId);
        if(dto != null){
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{userId}/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,@PathVariable Long userId){
        if(postService.deletePost(postId, userId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-comment")
    public ResponseEntity<?> addNewComment(@RequestBody CommentDto commentDto){
        CommentDto comment = commentService.addComment(commentDto);
        if(comment != null){
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/flag/{postId}")
    public ResponseEntity<?> flagPost(@PathVariable Long postId, @RequestBody boolean flag){
        return new ResponseEntity<>(postService.flagPost(postId, flag), HttpStatus.OK);
    }

    @GetMapping("/post-data")
    public ResponseEntity<?> getPostData() {
        return new ResponseEntity<>(postService.postData(), HttpStatus.OK);
    }
}
