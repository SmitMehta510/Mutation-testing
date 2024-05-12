package com.TranquilMind.controller;

import com.TranquilMind.dto.AuthDto;
import com.TranquilMind.service.UserService;
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
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@Tag(name = "User", description = "API for common functionality fot all users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Authenticate user.
     *
     * @param authDto The authentication details.
     * @return ResponseEntity containing the authentication result.
     */
    @Operation(summary = "Authenticate user", description = "Authenticate user with provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthDto authDto) {
        return new ResponseEntity<>(userService.authenticate(authDto), HttpStatus.OK);
    }


}
