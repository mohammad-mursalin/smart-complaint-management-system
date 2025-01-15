package com.mursalin.SCMS.service.impl;

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
    public ResponseEntity<?> addComplaint(String userEmail, Complaint complaint, MultipartFile imageFile) {
        try {
            User user = userUtil.getUserFromDB(userEmail);

            complaint.setCreatedAt(LocalDate.now());
            complaint.setStatus(String.valueOf(Status.PENDING));
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

    @Override
    public ResponseEntity<?> deleteComplaint(String userEmail, Long complaintId) {

        if (complaintRepository.existsById(complaintId)) {

            Complaint complaint = complaintRepository.findById(complaintId).get();

            if (complaint.getUser().getUserEmail().equals(userEmail)) {

                if( complaint.getStatus().equals(String.valueOf(Status.PENDING))) {

                    complaintRepository.deleteById(complaintId);

                    return new ResponseEntity<>("Complaint deleted successfully", HttpStatus.OK);
                }
                return new ResponseEntity<>("Can not delete due to IN_PROGRESS or RESOLVED state of the complaint", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Unauthorized to delete this complaint", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Complaint not found", HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<?> updateComplaint(String userEmail, Complaint complaint, MultipartFile imageFile) {
        try {
            if (complaintRepository.existsById(complaint.getComplaintId())) {
                Complaint complaintDB = complaintRepository.findById(complaint.getComplaintId()).get();

                if (complaintDB.getUser().getUserEmail().equals(userEmail)) {

                    if(complaint.getStatus().equals(String.valueOf(Status.PENDING))) {
                        complaintDB.setUpdatedAt(LocalDate.now());
                        complaintDB.setCategory(complaint.getCategory());
                        complaintDB.setDescription(complaint.getDescription());
                        complaintDB.setTitle(complaint.getTitle());
                        complaintDB.setImageName(generateUniqueFilename(imageFile.getOriginalFilename()));
                        complaintDB.setImageType(imageFile.getContentType());
                        complaintDB.setImageData(imageFile.getBytes());
                        complaintRepository.save(complaintDB);
                        return new ResponseEntity<>("Complaint updated successfully", HttpStatus.OK);
                    }
                    return new ResponseEntity<>("Can not update due to IN_PROGRESS or RESOLVED state of the complaint", HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity<>("Unauthorized to update this complaint", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>("Complaint not found", HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getComplaints(String userEmail) {
        long userId = userUtil.getUserFromDB(userEmail).getUserId();
        List<Complaint> complaints = complaintRepository.findComplaintsByUserId(userId);

        if (complaints == null || complaints.isEmpty()) {
            return new ResponseEntity<>("No complaints available now, please add some.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(complaints, HttpStatus.OK);
        }
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return System.currentTimeMillis() + "_" + originalFilename.hashCode() + extension;
    }

}
