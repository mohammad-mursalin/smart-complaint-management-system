package com.mursalin.SCMS.controller;

import com.mursalin.SCMS.service.AdminService;
import com.mursalin.SCMS.service.ComplaintService;
import com.mursalin.SCMS.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SCMS/admin/complaints")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping()
    public ResponseEntity<?> getComplaints() {
        return adminService.getComplaints();
    }

    @PostMapping("/updateStatus/{complaintId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long complaintId, @RequestBody String status) {
        return adminService.updateStatus(complaintId, status);
    }

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        return adminService.getUsers();
    }
}
