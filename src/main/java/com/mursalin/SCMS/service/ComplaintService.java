package com.mursalin.SCMS.service;

import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ComplaintService {
    void addComplaint(String userEmail, Complaint complaint, MultipartFile imageFile);

    void deleteComplaint(String userEmail, Long complaintId);

    void updateComplaint(String userEmail, Complaint complaint, MultipartFile imageFile);

    List<ComplaintDTO> getComplaints(String userEmail);
}
