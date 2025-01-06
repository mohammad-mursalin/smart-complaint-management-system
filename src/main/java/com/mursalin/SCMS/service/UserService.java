package com.mursalin.SCMS.service;

import com.mursalin.SCMS.dto.LoginRegisterRequest;
import com.mursalin.SCMS.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> register(User user);

    ResponseEntity<?> verifyToken(String token);

    ResponseEntity<String> login(@Valid LoginRegisterRequest user);
}
