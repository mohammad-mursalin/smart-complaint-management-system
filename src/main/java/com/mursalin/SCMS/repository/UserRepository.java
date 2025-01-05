package com.mursalin.SCMS.repository;

import com.mursalin.SCMS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
