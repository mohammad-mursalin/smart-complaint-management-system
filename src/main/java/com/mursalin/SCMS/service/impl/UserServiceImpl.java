package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.LoginRegisterRequest;
import com.mursalin.SCMS.exceptionHandler.*;
import com.mursalin.SCMS.jwt.JwtService;
import com.mursalin.SCMS.model.Confirmation;
import com.mursalin.SCMS.model.Role;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.ConfirmationRepository;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.MailService;
import com.mursalin.SCMS.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    
    private final ConfirmationRepository confirmationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, ConfirmationRepository confirmationRepository, MailService mailService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.confirmationRepository = confirmationRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public User register(User user) {
        if(!userRepository.existsByUserEmailIgnoreCase(user.getUserEmail())) {
            user.setRole(String.valueOf(Role.USER));
            user.setEnable(false);
            user.setPassword(encoder.encode(user.getPassword()));
            Confirmation confirmation = new Confirmation(user);
            userRepository.save(user);
            confirmationRepository.save(confirmation);
            mailService.sendSimpleMail(user.getUserName(), user.getUserEmail(),confirmation.getToken());
            return user;
        }
        throw new CustomException("User already exists with this email : " + user.getUserEmail(),HttpStatus.CONFLICT);
    }

    @Override
    public String login(LoginRegisterRequest user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getPassword()));

            log.info("inside login of userServiceImpl");
            User authenticatedUser = userRepository.findByUserEmailIgnoreCase(user.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (authenticatedUser.isEnable()) {
                if (authentication.isAuthenticated()) {
                    return jwtService.generateToken(user.getUserEmail());
                }
                throw new AuthenticationFailedException("Login failed due to invalid credentials.");
            }
            throw new UserNotVerifiedException("User is not verified. Please verify your email.");

        } catch (AuthenticationException ex) {
            throw new AuthenticationFailedException("Invalid email or password.");
        }
    }


    @Override
    @Transactional
    public String verifyToken(String token) {
        Optional<Confirmation> confirmation = confirmationRepository.findByToken(token);

        if(confirmation.isPresent()) {
            User user = confirmation.get().getUser();
            if(!user.isEnable()) {
                user.setEnable(true);
                userRepository.save(user);
                confirmationRepository.delete(confirmation.get());
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserEmail());
                UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(upaToken);
                return jwtService.generateToken(user.getUserEmail());
            }
            throw new UserAlreadyExistsException("User is already verified.");
        }
        throw new UserNotFoundException("Verification token is invalid or expired.");
    }
}
