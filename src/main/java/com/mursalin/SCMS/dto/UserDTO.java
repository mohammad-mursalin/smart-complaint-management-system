package com.mursalin.SCMS.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<ComplaintDTO> complaintDTOS;

    public UserDTO(Long userId, String userName, String userEmail, List<ComplaintDTO> complaintDTOS) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.complaintDTOS = complaintDTOS;
    }

    public UserDTO(Long userId, String userName, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;

    }

    public UserDTO(Long userId, String userName, String userEmail, String password, String role) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.role = role;
    }
}
