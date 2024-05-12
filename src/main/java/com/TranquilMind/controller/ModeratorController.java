package com.TranquilMind.controller;

import com.TranquilMind.dto.PasswordDto;
import com.TranquilMind.service.ModeratorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/moderator")
@Tag(name = "Moderator", description = "API regarding moderator functionalities")
public class ModeratorController {

    @Autowired
    private ModeratorService moderatorService;

    /**
     * Retrieve flagged posts.
     *
     * @return Flagged posts.
     */
    @Operation(summary = "Get flagged posts", description = "Retrieve flagged posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/flagged-posts")
    public ResponseEntity<?> getFlaggedPosts() {
        return new ResponseEntity<>(moderatorService.getFlaggedPosts(),HttpStatus.OK);
    }

    /**
     * Unflag a post.
     *
     * @param id      The ID of the post.
     * @param unflag  Unflag status (true/false).
     * @return A message confirming unflagging.
     */
    @Operation(summary = "Unflag a post", description = "Unflag a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post unflagged successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/unflag/{id}")
    public ResponseEntity<?> unflagPost(@PathVariable Long id,@RequestBody Boolean unflag) {
        return new ResponseEntity<>(moderatorService.unflagPost(id,unflag),HttpStatus.OK);
    }

    /**
     * Disable a post.
     *
     * @param id      The ID of the post.
     * @param disable Disable status (true/false).
     * @return A message confirming disabling.
     */
    @Operation(summary = "Disable a post", description = "Disable a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post disabled successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disablePost(@PathVariable Long id,@RequestBody Boolean disable) {
        return new ResponseEntity<>(moderatorService.disablePost(id,disable),HttpStatus.OK);
    }

    /**
     * Retrieve unapproved answers.
     *
     * @return Unapproved answers.
     */
    @Operation(summary = "Get unapproved answers", description = "Retrieve unapproved answers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")})
    })
    @GetMapping("/unapproved-answers")
    public ResponseEntity<?> getUnapprovedAnswers() {
        return new ResponseEntity<>(moderatorService.getAnsweredQuestions(),HttpStatus.OK);
    }

    /**
     * Approve an answer.
     *
     * @param questionId The ID of the question.
     * @return A message confirming approval.
     */
    @Operation(summary = "Approve an answer", description = "Approve an answer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer approved successfully"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    @PutMapping("/approve-answer/{questionId}")
    public ResponseEntity<?> approveAnswer(@PathVariable Long questionId) {
        return new ResponseEntity<>(moderatorService.approveAnswer(questionId),HttpStatus.OK);
    }

    /**
     * Retrieve a moderator by user ID.
     *
     * @param userId The ID of the user.
     * @return The moderator details.
     */
    @Operation(summary = "Get moderator by user ID", description = "Retrieve a moderator by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Moderator not found")
    })
    @GetMapping("/getbyid/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(moderatorService.getModeratorByUserId(userId),HttpStatus.OK);
    }

    /**
     * Update moderator password.
     *
     * @param passwordDto The new password.
     * @return A message confirming password update.
     */
    @Operation(summary = "Update moderator password", description = "Update moderator password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto){
        return new ResponseEntity<>(moderatorService.updatePassword(passwordDto),HttpStatus.OK);
    }
}
