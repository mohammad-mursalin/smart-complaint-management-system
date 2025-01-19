package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.model.UserPrinciples;
import com.mursalin.SCMS.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("useremail {} " ,email);
        Optional<UserDTO> user = repo.findUserDTOByUserEmail(email);
        log.info("user here {} " ,user.isPresent());
        if(user.isPresent()){
            return new UserPrinciples(user.get());}
        else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
