package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public ResponseEntity<?> getComplaints() {
        return null;
    }

    @Override
    public ResponseEntity<?> updateStatus(String status) {
        return null;
    }

    @Override
    public ResponseEntity<?> getUsers() {
        return null;
    }
}
