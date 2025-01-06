package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.model.UserPrinciples;
import com.mursalin.SCMS.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public MyUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDTO> user = repo.findUserDTOByEmail(email);

        if(user.isPresent()){
            return new UserPrinciples(user.get());}
        else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
