package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SCMS/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ResponseEntity<?> userRegistration(@RequestBody User user) {

        if(user != null) {

            return userService.register(user);
        }
        return new ResponseEntity<>("user should not be null", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> verifyToken(@RequestParam String token) {

        return userService.verifyToken(token);
    }

}
