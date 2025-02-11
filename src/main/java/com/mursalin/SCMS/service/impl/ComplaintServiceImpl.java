package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.dto.ImageResponse;
import com.mursalin.SCMS.exceptionHandler.ComplaintNotFoundException;
import com.mursalin.SCMS.exceptionHandler.InvalidComplaintStateException;
import com.mursalin.SCMS.exceptionHandler.UnauthorizedActionException;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.Status;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.service.ComplaintService;
import com.mursalin.SCMS.service.ImageService;
import com.mursalin.SCMS.utils.Mapper;
import com.mursalin.SCMS.utils.UserUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final UserUtil userUtil;
    private final ComplaintRepository complaintRepository;
    private final ImageService imageService;
    private final Mapper mapper = new Mapper();

    public ComplaintServiceImpl(UserUtil userUtil, ComplaintRepository complaintRepository, ImageService imageService) {
        this.userUtil = userUtil;
        this.complaintRepository = complaintRepository;
        this.imageService = imageService;
    }

    @Override
    public void addComplaint(String userEmail, Complaint complaint, MultipartFile imageFile) {
        try {
            User user = userUtil.getUserFromDB(userEmail);
            complaint.setCreatedAt(LocalDate.now());
            complaint.setStatus(String.valueOf(Status.PENDING));
            if (imageFile != null && !imageFile.isEmpty()) {
                ImageResponse image = imageService.uploadImage(imageFile);
                complaint.setImageUrl(image.getImageUrl());
                complaint.setDeleteHash(image.getDeleteHash());
            }
            complaint.setUser(user);

            complaintRepository.save(complaint);
        } catch (IOException e) {
            throw new RuntimeException("Error while processing the complaint image");
        }
    }

    @Override
    public void deleteComplaint(String userEmail, Long complaintId) {

        Complaint complaint = complaintRepository.findComplaintByIdAndUserEmail(complaintId, userEmail)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found"));

        if( complaint.getStatus().equalsIgnoreCase(String.valueOf(Status.PENDING))) {

            imageService.deleteImage(complaint.getDeleteHash());
            complaintRepository.deleteById(complaintId);
            return;
        }
        throw new InvalidComplaintStateException("Can not delete complaint due to in progress or resolved state");
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
            if (imageFile != null && !imageFile.isEmpty()) {
                imageService.deleteImage(complaintDB.getDeleteHash());
                ImageResponse image = imageService.uploadImage(imageFile);
                complaintDB.setImageUrl(image.getImageUrl());
                complaintDB.setDeleteHash(image.getDeleteHash());
            }
            complaintRepository.save(complaintDB);
        } catch (IOException e) {
            throw new RuntimeException("Error while updating the complaint image");
        }
    }

    @Override
    public List<ComplaintDTO> getComplaints(String userEmail) {
        Long userId = userUtil.getUserFromDB(userEmail).getUserId();
        List<Complaint> complaints = complaintRepository.findComplaintsByUserId(userId);

        return complaints.stream().map(mapper::mapToComplaintDTO).collect(Collectors.toList());
    }

}
