package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.LoginRegisterRequest;
import com.mursalin.SCMS.jwt.JwtService;
import com.mursalin.SCMS.model.Confirmation;
import com.mursalin.SCMS.model.Role;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.ConfirmationRepository;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.MailService;
import com.mursalin.SCMS.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ConfirmationRepository confirmationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, ConfirmationRepository confirmationRepository, MailService mailService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.confirmationRepository = confirmationRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<?> register(User user) {
        if(!userRepository.existsByUserEmailIgnoreCase(user.getUserEmail())) {
            user.setRole(String.valueOf(Role.USER));
            user.setEnable(false);
            user.setPassword(encoder.encode(user.getPassword()));
            Confirmation confirmation = new Confirmation(user);
            userRepository.save(user);
            confirmationRepository.save(confirmation);
            mailService.sendSimpleMail(user.getUserName(), user.getUserEmail(),confirmation.getToken());
            return new ResponseEntity<>("user registration successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("User already exists with this email", HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<String> login(LoginRegisterRequest user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getPassword()));

            logger.info("inside login of userServiceImpl");
            User authenticatedUser = userRepository.findByUserEmailIgnoreCase(user.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (authenticatedUser.isEnable()) {
                if (authentication.isAuthenticated()) {
                    String jwtToken = jwtService.generateToken(user.getUserEmail());
                    return new ResponseEntity<>(jwtToken, HttpStatus.OK);
                }
                return new ResponseEntity<>("Login failed", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("User is not verified. Please verify your email.", HttpStatus.FORBIDDEN);

        } catch (Exception e) {
            logger.info(encoder.encode(user.getPassword()));
            return new ResponseEntity<>("Invalid credentials " +e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> verifyToken(String token) {
        Optional<Confirmation> confirmation = confirmationRepository.findByToken(token);

        if(confirmation.isPresent()) {
            User user = confirmation.get().getUser();
            if(!user.isEnable()) {
                user.setEnable(true);
                userRepository.save(user);
                confirmationRepository.delete(confirmation.get());
                String jwt = jwtService.generateToken(user.getUserEmail());
                return new ResponseEntity<>(jwt, HttpStatus.OK);
            }
            return new ResponseEntity<>("user already verified", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("user verification failed", HttpStatus.UNAUTHORIZED);
    }
}
