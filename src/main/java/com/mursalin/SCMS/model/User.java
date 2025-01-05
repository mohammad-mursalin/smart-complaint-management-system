package com.mursalin.SCMS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private long userId;
    private String userName;
    private String password;
    private String userEmail;
    private boolean isEnable;
    private Role role = Role.USER;

    @OneToMany
    private List<Complaint> complaints;
}
