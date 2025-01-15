package com.mursalin.SCMS.repository;

import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmailIgnoreCase(String email);

    boolean existsByUserEmailIgnoreCase(String email);

    @Query("SELECT new com.mursalin.SCMS.dto.UserDTO(u.userId, u.userName, u.userEmail, u.password, u.role) FROM User u WHERE u.userEmail = :email")
    Optional<UserDTO> findUserDTOByUserEmail(String email);

    @Query("SELECT new com.mursalin.SCMS.dto.UserDTO(u.userId, u.userName, u.userEmail) FROM User u WHERE u.isEnable = true")
    List<UserDTO> findAllUsers();
}
