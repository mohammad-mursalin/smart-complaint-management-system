package com.mursalin.SCMS.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "confirmations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Confirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Confirmation(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
    }

}
