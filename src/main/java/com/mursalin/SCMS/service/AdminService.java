package com.mursalin.SCMS.service;

import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<?> getComplaints();

    ResponseEntity<?> updateStatus(Long complaintId, String status);

    ResponseEntity<?> getUsers();
}
