package com.mursalin.SCMS.utils;

import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    private final UserRepository userRepository;

    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromDB(String userEmail) {
        return userRepository.findByUserEmailIgnoreCase(userEmail).get();
    }
}
