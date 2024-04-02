package com.TranquilMind.controller;

import com.TranquilMind.dto.AuthDto;
import com.TranquilMind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //RessourceEndPoint:http://localhost:8087/api/user/register
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
//        return userService.register(registerDto);
//    }

    //RessourceEndPoint:http://localhost:8087/api/user/authenticate
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthDto authDto) {
        return new ResponseEntity<>(userService.authenticate(authDto), HttpStatus.OK);
    }


}