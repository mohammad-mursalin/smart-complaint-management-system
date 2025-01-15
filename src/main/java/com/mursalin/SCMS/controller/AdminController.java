package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.service.AdminService;
import com.mursalin.SCMS.service.ComplaintService;
import com.mursalin.SCMS.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SCMS/admin/complaints")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping()
    public ResponseEntity<?> getComplaints() {
        return adminService.getComplaints();
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(String status) {
        return adminService.updateStatus(status);
    }

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        return adminService.getUsers();
    }
}
