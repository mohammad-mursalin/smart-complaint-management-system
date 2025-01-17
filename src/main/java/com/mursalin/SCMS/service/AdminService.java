package com.mursalin.SCMS.service;

import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.model.Complaint;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    List<ComplaintDTO> getComplaints();

    Complaint updateStatus(Long complaintId, String status);

    List<UserDTO> getUsers();
}
