package com.mursalin.SCMS.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long userId;
    private String userName;
    private String userEmail;
    private String password;
    private String role;

    public UserDTO(Long userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
