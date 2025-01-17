package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.exceptionHandler.ComplaintNotFoundException;
import com.mursalin.SCMS.exceptionHandler.InvalidComplaintStateException;
import com.mursalin.SCMS.exceptionHandler.UnauthorizedActionException;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.Status;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.service.ComplaintService;
import com.mursalin.SCMS.utils.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final UserUtil userUtil;
    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(UserUtil userUtil, ComplaintRepository complaintRepository) {
        this.userUtil = userUtil;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public void addComplaint(String userEmail, Complaint complaint, MultipartFile imageFile) {
        try {
            User user = userUtil.getUserFromDB(userEmail);
            complaint.setCreatedAt(LocalDate.now());
            complaint.setStatus(String.valueOf(Status.PENDING));
            complaint.setImageName(generateUniqueFilename(imageFile.getOriginalFilename()));
            complaint.setImageType(imageFile.getContentType());
            complaint.setImageData(imageFile.getBytes());
            complaint.setUser(user);

            complaintRepository.save(complaint);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing the complaint image");
        }
    }

    @Override
    public void deleteComplaint(String userEmail, Long complaintId) {

        if (complaintRepository.existsById(complaintId)) {

            Complaint complaint = complaintRepository.findById(complaintId).get();

            if (complaint.getUser().getUserEmail().equals(userEmail)) {

                if( complaint.getStatus().equals(String.valueOf(Status.PENDING))) {

                    complaintRepository.deleteById(complaintId);
                }
                throw new InvalidComplaintStateException("Can not delete complaint due to in progress or resolved state");
            }
            throw new UnauthorizedActionException("You are not authorized to delete this complaint");
        }
        throw new ComplaintNotFoundException("Complaint with id " + complaintId + " not found");
    }


    @Override
    public void updateComplaint(String userEmail, Complaint complaint, MultipartFile imageFile) {
        Complaint complaintDB = complaintRepository.findById(complaint.getComplaintId())
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found"));

        if (!complaintDB.getUser().getUserEmail().equals(userEmail)) {
            throw new UnauthorizedActionException("Unauthorized to update this complaint");
        }

        if (!complaintDB.getStatus().equals(String.valueOf(Status.PENDING))) {
            throw new InvalidComplaintStateException("Cannot update due to IN_PROGRESS or RESOLVED state of the complaint");
        }

        try {
            complaintDB.setUpdatedAt(LocalDate.now());
            complaintDB.setCategory(complaint.getCategory());
            complaintDB.setDescription(complaint.getDescription());
            complaintDB.setTitle(complaint.getTitle());
            complaintDB.setImageName(generateUniqueFilename(imageFile.getOriginalFilename()));
            complaintDB.setImageType(imageFile.getContentType());
            complaintDB.setImageData(imageFile.getBytes());
            complaintRepository.save(complaintDB);
        } catch (IOException e) {
            throw new RuntimeException("Error while updating the complaint image");
        }
    }

    @Override
    public List<ComplaintDTO> getComplaints(String userEmail) {
        Long userId = userUtil.getUserFromDB(userEmail).getUserId();
        return complaintRepository.findComplaintsByUserId(userId);
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return System.currentTimeMillis() + "_" + originalFilename.hashCode() + extension;
    }

}
