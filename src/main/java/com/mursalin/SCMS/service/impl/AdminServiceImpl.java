package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.dto.UserDTO;
import com.mursalin.SCMS.exceptionHandler.ComplaintNotFoundException;
import com.mursalin.SCMS.exceptionHandler.CustomException;
import com.mursalin.SCMS.exceptionHandler.UnauthorizedActionException;
import com.mursalin.SCMS.model.Comment;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.CommentRepository;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.repository.UserRepository;
import com.mursalin.SCMS.service.AdminService;
import com.mursalin.SCMS.utils.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;
    private final CommentRepository commentRepository;
    private final Mapper mapper = new Mapper();

    public AdminServiceImpl(UserRepository userRepository, ComplaintRepository complaintRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;

        this.commentRepository = commentRepository;
    }

    @Override
    public List<UserDTO> getComplaints(String status) {

        List<User> users = userRepository.findAllUsersWithComplaints(status);

        return users.stream().map(mapper::mapToUserDTO).collect(Collectors.toList());
    }

    @Override
    public Complaint updateStatus(Long complaintId, String status) {

        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new ComplaintNotFoundException("Unavailable complaint with id : " + complaintId));

        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    @Transactional
    public void addComment(CommentDTO commentDto, Long complaintId) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found"));

        Comment comment = Comment.builder()
                .createdAt(LocalDateTime.now())
                .comment(commentDto.getComment())
                .commentedBy("ADMIN")
                .complaint(complaint)
                .build();

        complaint.setComments(comment);

        commentRepository.save(comment);

    }

    @Override
    @Transactional
    public void updateComment(CommentDTO commentDto, Long complaintId) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found"));

        Comment comment = commentRepository.findById(commentDto.getCommentId())
                .orElseThrow(() -> new CustomException("Comment not found", HttpStatus.NOT_FOUND));

        if (!comment.getComplaint().getComplaintId().equals(complaintId)) {
            throw new CustomException("Comment does not belong to the specified complaint", HttpStatus.BAD_REQUEST);
        }

        comment.setComment(commentDto.getComment());
        comment.setEditedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }
}
