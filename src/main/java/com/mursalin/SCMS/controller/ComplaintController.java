package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.service.CommentService;
import com.mursalin.SCMS.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/SCMS/user")
@Slf4j
public class ComplaintController {

    private final ComplaintService complaintService;
    private final CommentService commentService;

    public ComplaintController(ComplaintService complaintService, CommentService commentService) {
        this.complaintService = complaintService;
        this.commentService = commentService;
    }

    @PostMapping("/addComplaint")
    public ResponseEntity<?> addComplaint(@RequestPart Complaint complaint, @RequestPart(required = false) MultipartFile imageFile, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        complaintService.addComplaint(userEmail, complaint, imageFile);
        return new ResponseEntity<>("New complaint added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/myComplaints")
    public ResponseEntity<?> getComplaints(@AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails.getUsername());
        String userEmail = userDetails.getUsername();
        log.info(userEmail);
        List<ComplaintDTO> complaints = complaintService.getComplaints(userEmail);
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }

    @PutMapping("/updateComplaint")
    public ResponseEntity<?> updateComplaints(@AuthenticationPrincipal UserDetails userDetails, @RequestPart Complaint complaint, @RequestPart(required = false) MultipartFile imageFile) {
        String userEmail = userDetails.getUsername();
        complaintService.updateComplaint(userEmail, complaint, imageFile);
        return new ResponseEntity<>("Complaint updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteComplaint/{complaintId}")
    public ResponseEntity<?> DeleteComplaints(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long complaintId) {
        String userEmail = userDetails.getUsername();
        complaintService.deleteComplaint(userEmail, complaintId);
        return new ResponseEntity<>("Complaint deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/addComment/{complaintId}")
    public ResponseEntity<?> addComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentDTO commentDto, @PathVariable Long complaintId) {
        String userEmail = userDetails.getUsername();
        commentService.addComment(userEmail, commentDto, complaintId);
        return new ResponseEntity<>("New comment added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateComment/{complaintId}")
    public ResponseEntity<?> updateComment(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CommentDTO comment, @PathVariable Long complaintId) {
        String userEmail = userDetails.getUsername();
        commentService.updateComment(userEmail, comment, complaintId);
        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
    }
}
