package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.dto.LoginRegisterRequest;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SCMS/newUser")
public class NewUserController {

    private final UserService userService;

    public NewUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRegisterRequest user) {
        return userService.login(user);
    }
}
