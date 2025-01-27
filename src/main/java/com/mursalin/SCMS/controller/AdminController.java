package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.service.AdminService;
import com.mursalin.SCMS.service.ComplaintService;
import com.mursalin.SCMS.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SCMS/admin/complaints")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<?> getComplaints(@RequestParam (required = false) String status) {
        List<ComplaintDTO> complaintDTOS = adminService.getComplaints(status);
        if(complaintDTOS.isEmpty())
            return new ResponseEntity<>("No complaints found with status: " + status, HttpStatus.OK);
        return new ResponseEntity<>(complaintDTOS, HttpStatus.OK);
    }

    @PostMapping("/updateStatus/{complaintId}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Long complaintId, @PathVariable String status) {
        Complaint complaint = adminService.updateStatus(complaintId, status);
        return new ResponseEntity<>(complaint, HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> getUsers() {
        List<UserDTO> users =  adminService.getUsers();
        if(users.isEmpty())
            return new ResponseEntity<>("No users found", HttpStatus.OK);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/addComment/{complaintId}")
    public ResponseEntity<?> addComment(@RequestBody CommentDTO comment, @PathVariable Long complaintId) {
        adminService.addComment(comment, complaintId);
        return new ResponseEntity<>("New comment added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateComment/{complaintId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO comment, @PathVariable Long complaintId) {
        adminService.updateComment(comment, complaintId);
        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
    }
}
