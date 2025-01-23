package com.mursalin.SCMS.service;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.model.Complaint;

import java.util.List;

public interface AdminService {
    List<ComplaintDTO> getComplaints(String status);

    Complaint updateStatus(Long complaintId, String status);

    List<UserDTO> getUsers();

    void addComment(CommentDTO comment, Long complaintId);

    void updateComment(CommentDTO comment, Long complaintId);
}
