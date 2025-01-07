package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.dto.LoginRegisterRequest;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.service.ComplaintService;
import com.mursalin.SCMS.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/SCMS/user")
public class UserController {

    private final UserService userService;

    private final ComplaintService complaintService;

    public UserController(UserService userService, ComplaintService complaintService) {
        this.userService = userService;
        this.complaintService = complaintService;
    }

    @PostMapping("/addComplaint")
    public ResponseEntity<?> addComplaint(@RequestPart Complaint complaint, @RequestPart MultipartFile imageFile, @AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return complaintService.addComplaint(userEmail, complaint, imageFile);
    }

}
