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

    private String imageUrl;

    private UserDTO user;

        public ComplaintDTO(Long complaintId, String title, String description, String category, String status, LocalDate createdAt, LocalDate updatedAt, String imageUrl) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
    }
}
