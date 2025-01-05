package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> register(User user) {

        user.isEnable(false);
        userRepository.save(user);
        return new ResponseEntity<>("user registration successful", HttpStatus.OK);
    }
}
