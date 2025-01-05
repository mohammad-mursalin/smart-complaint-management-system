package com.mursalin.SCMS.service;

import com.mursalin.SCMS.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> register(User user);

    ResponseEntity<?> verifyToken(String token);
}
