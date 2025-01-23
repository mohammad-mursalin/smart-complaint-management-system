package com.mursalin.SCMS.service;

import com.mursalin.SCMS.dto.CommentDTO;

public interface CommentService {
    void addComment(String userEmail, CommentDTO commentDto, Long complaintId);

    void updateComment(String userEmail, CommentDTO commentDto, Long complaintId);
}
