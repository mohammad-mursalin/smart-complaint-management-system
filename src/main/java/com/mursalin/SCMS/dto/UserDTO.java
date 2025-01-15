package com.mursalin.SCMS.dto;

import com.mursalin.SCMS.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class UserDTO {
    private long userId;
    private String userName;
    private String userEmail;
    private String password;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDTO() {
    }

    public UserDTO(long userId, String userEmail, String password, String userName, String role) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.userName = userName;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String role) {
        this.userName = userName;
    }
}
