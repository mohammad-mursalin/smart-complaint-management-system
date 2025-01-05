package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.model.Confirmation;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.ConfirmationRepository;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.MailService;
import com.mursalin.SCMS.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConfirmationRepository confirmationRepository;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, ConfirmationRepository confirmationRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.confirmationRepository = confirmationRepository;
        this.mailService = mailService;
    }

    @Override
    public ResponseEntity<?> register(User user) {

        if(!userRepository.existsByEmailIgnoreCase(user.getUserEmail())) {
            user.setEnable(false);
            Confirmation confirmation = new Confirmation(user);
            userRepository.save(user);
            confirmationRepository.save(confirmation);
            mailService.sendSimpleMail(user.getUserName(), user.getUserEmail(),confirmation.getToken());
            return new ResponseEntity<>("user registration successful", HttpStatus.OK);
        }
        throw new RuntimeException("user exist by this email");
    }

    @Override
    public ResponseEntity<?> verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        User user = confirmation.getUser();
        user.setEnable(true);
        userRepository.save(user);
        return new ResponseEntity<>("user verified", HttpStatus.OK);
    }
}
