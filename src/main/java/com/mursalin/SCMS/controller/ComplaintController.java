package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.service.ComplaintService;
import com.mursalin.SCMS.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/SCMS/user")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/addComplaint")
    public ResponseEntity<?> addComplaint(@RequestPart Complaint complaint, @RequestPart MultipartFile imageFile, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return complaintService.addComplaint(userEmail, complaint, imageFile);
    }

    @GetMapping("/myComplaints")
    public ResponseEntity<?> getComplaints(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return complaintService.getComplaints(userEmail);
    }

    @PutMapping("/updateComplaint")
    public ResponseEntity<?> updateComplaints(@AuthenticationPrincipal UserDetails userDetails, @RequestPart Complaint complaint, @RequestPart MultipartFile imageFile) {
        String userEmail = userDetails.getUsername();
        return complaintService.updateComplaint(userEmail, complaint, imageFile);
    }

    @DeleteMapping("/deleteComplaint/{complaintId}")
    public ResponseEntity<?> updateComplaints(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long complaintId) {
        String userEmail = userDetails.getUsername();
        return complaintService.deleteComplaint(userEmail, complaintId);
    }
}
