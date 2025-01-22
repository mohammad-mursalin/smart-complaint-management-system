package com.mursalin.SCMS.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
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

    private List<CommentDTO> commentDTO;

    public ComplaintDTO(Long complaintId, String title, String description, String category, String status,
                        LocalDate createdAt, LocalDate updatedAt, String imageUrl, List<CommentDTO> commentDTO) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imageUrl = imageUrl;
        this.commentDTO = commentDTO;
    }

}
