package com.mursalin.SCMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long complaintId;
    private String title;
    private String description;
    private String category;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;

    @JsonIgnore
    @ManyToOne
    private User user;
}
