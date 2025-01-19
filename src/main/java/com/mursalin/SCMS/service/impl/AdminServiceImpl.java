package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.exceptionHandler.ComplaintNotFoundException;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;

    public AdminServiceImpl(UserRepository userRepository, ComplaintRepository complaintRepository) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public List<ComplaintDTO> getComplaints() {

        return complaintRepository.findAllComplaintsWithUser();
    }

    @Override
    public Complaint updateStatus(Long complaintId, String status) {

        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new ComplaintNotFoundException("Unavailable complaint with id : " + complaintId));

        return complaintRepository.save(complaint);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAllUsers();
    }
}
