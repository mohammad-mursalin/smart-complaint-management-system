package com.mursalin.SCMS.service;

import com.mursalin.SCMS.dto.LoginRegisterRequest;
import com.mursalin.SCMS.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User register(User user);

    String verifyToken(String token);

    String login(@Valid LoginRegisterRequest user);
}
