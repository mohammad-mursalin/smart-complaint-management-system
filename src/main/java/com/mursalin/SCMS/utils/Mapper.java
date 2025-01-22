package com.mursalin.SCMS.utils;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.model.Complaint;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public ComplaintDTO mapToComplaintDTO(Complaint complaint) {
        List<CommentDTO> commentDTOs = complaint.getComments().stream()
                .map(comment -> new CommentDTO(
                        comment.getCommentId(),
                        comment.getComment(),
                        comment.getCreatedAt(),
                        comment.getEditedAt(),
                        comment.getCommentedBy()))
                .collect(Collectors.toList());

        return new ComplaintDTO(
                complaint.getComplaintId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getCreatedAt(),
                complaint.getUpdatedAt(),
                complaint.getImageUrl(),
                commentDTOs
        );
    }
}
