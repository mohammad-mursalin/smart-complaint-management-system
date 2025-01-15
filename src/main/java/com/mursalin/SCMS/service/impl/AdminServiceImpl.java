package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;

    public AdminServiceImpl(UserRepository userRepository, ComplaintRepository complaintRepository) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public ResponseEntity<?> getComplaints() {

        List<ComplaintDTO> complaints = complaintRepository.findAllComplaintsWithUser();
        if(!complaints.isEmpty())
            return new ResponseEntity<>(complaints, HttpStatus.OK);
        return new ResponseEntity<>("There are no complaints right now", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateStatus(String status) {
        return null;
    }

    @Override
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users = userRepository.findAllUsers();

        if(!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>("There are no users right now", HttpStatus.OK);
    }
}
