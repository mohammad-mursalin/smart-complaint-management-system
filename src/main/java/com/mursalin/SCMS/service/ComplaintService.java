package com.mursalin.SCMS.service;

import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ComplaintService {
    ResponseEntity<?> addComplaint(String userEmail, Complaint complaint, MultipartFile imageFile);
}
