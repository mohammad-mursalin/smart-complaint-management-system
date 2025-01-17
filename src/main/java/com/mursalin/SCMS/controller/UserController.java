package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.dto.LoginRegisterRequest;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SCMS/newUser")
public class UserController {

    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@RequestBody User user) {

        if(user != null) {
            User registeredUser =  userService.register(user);
            return new ResponseEntity<>("Registration successful for " + registeredUser.getUserName(), HttpStatus.OK);
        }
        return new ResponseEntity<>("user should not be null", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        return new ResponseEntity<>(userService.verifyToken(token),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRegisterRequest user) {
        logger.info(user.getUserEmail());
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }
}
