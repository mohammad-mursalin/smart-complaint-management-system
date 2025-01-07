package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

public class ComplaintServiceImpl implements ComplaintService {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(UserRepository userRepository, ComplaintRepository complaintRepository) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public ResponseEntity<?> addComplaint(String userEmail, Complaint complaint, MultipartFile imageFile) {
        try {
            User user = getUserFromDB(userEmail);

            complaint.setCreatedAt(LocalDate.now());
            complaint.setImageName(generateUniqueFilename(imageFile.getOriginalFilename()));
            complaint.setImageType(imageFile.getContentType());
            complaint.setImageData(imageFile.getBytes());
            complaint.setUser(user);

            complaintRepository.save(complaint);

            return new ResponseEntity<>("new complaint added successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    private User getUserFromDB(String userEmail) {
        return userRepository.findByUserEmailIgnoreCase(userEmail);
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return System.currentTimeMillis() + "_" + originalFilename.hashCode() + extension;
    }

}
