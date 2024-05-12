package com.TranquilMind.controller;

import com.TranquilMind.dto.CommentDto;
import com.TranquilMind.dto.PostDto;
import com.TranquilMind.model.Comment;
import com.TranquilMind.model.Post;
import com.TranquilMind.service.CommentService;
import com.TranquilMind.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/post")
@Tag(name = "Post",description = "API for all post and comment functionalities")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    /**
     * Retrieve all posts.
     *
     * @return A list of all posts.
     */
    @Operation(summary = "Get all posts", description = "Retrieve all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/get-posts")
    public ResponseEntity<?> getPosts(){
        return new ResponseEntity<>(postService.getAllPostsByTime(), HttpStatus.OK);
    }

    /**
     * Add a new post.
     *
     * @param postDto The details of the new post.
     * @return The newly added post.
     */
    @Operation(summary = "Add a new post", description = "Add a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add-post")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        PostDto post = postService.addPost(postDto);

        if(post != null){
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Edit an existing post.
     *
     * @param postId The ID of the post to be edited.
     * @param userId The ID of the user who owns the post.
     * @param postdto The updated details of the post.
     * @return The updated post information.
     */
    @Operation(summary = "Edit an existing post", description = "Edit an existing post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post edited successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
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

    /**
     * Delete a post.
     *
     * @param postId The ID of the post to be deleted.
     * @param userId The ID of the user who owns the post.
     * @return HttpStatus.OK if successful, HttpStatus.BAD_REQUEST otherwise.
     */
    @Operation(summary = "Delete a post", description = "Delete a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @DeleteMapping("{userId}/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,@PathVariable Long userId){
        if(postService.deletePost(postId, userId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Add a new comment to a post.
     *
     * @param commentDto The details of the new comment.
     * @return The newly added comment.
     */
    @Operation(summary = "Add a new comment", description = "Add a new comment to a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment added successfully", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add-comment")
    public ResponseEntity<?> addNewComment(@RequestBody CommentDto commentDto){
        CommentDto comment = commentService.addComment(commentDto);
        if(comment != null){
            return new ResponseEntity<>(comment, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Flag or unflag a post.
     *
     * @param postId The ID of the post.
     * @param flag Flag status (true/false).
     * @return HttpStatus.OK if successful.
     */
    @Operation(summary = "Flag or unflag a post", description = "Flag or unflag a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post flag status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/flag/{postId}")
    public ResponseEntity<?> flagPost(@PathVariable Long postId, @RequestBody boolean flag){
        return new ResponseEntity<>(postService.flagPost(postId, flag), HttpStatus.OK);
    }

    /**
     * Retrieve post data.
     *
     * @return Post data.
     */
    @Operation(summary = "Get post data", description = "Retrieve post data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/post-data")
    public ResponseEntity<?> getPostData() {
        return new ResponseEntity<>(postService.postData(), HttpStatus.OK);
    }
}
