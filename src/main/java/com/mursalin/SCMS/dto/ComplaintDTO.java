package com.mursalin.SCMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ComplaintDTO {

    private Long complaintId;

    private String title;

    private String description;

    private String category;

    private String status;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String imageName;
    private String imageType;

    private byte[] imageData;

    private UserDTO user;

        public ComplaintDTO(Long complaintId, String title, String description, String category, String status, LocalDate createdAt, LocalDate updatedAt, String imageName, String imageType, byte[] imageData) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData = imageData;
    }
}
