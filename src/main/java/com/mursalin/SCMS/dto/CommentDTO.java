package com.mursalin.SCMS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private Long commentId;

    private String comment;

    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    private String commentedBy;
}
