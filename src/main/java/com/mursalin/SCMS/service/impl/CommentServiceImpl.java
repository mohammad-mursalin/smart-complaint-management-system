package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.exceptionHandler.ComplaintNotFoundException;
import com.mursalin.SCMS.exceptionHandler.CustomException;
import com.mursalin.SCMS.exceptionHandler.UnauthorizedActionException;
import com.mursalin.SCMS.model.Comment;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.model.User;
import com.mursalin.SCMS.repository.CommentRepository;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.service.CommentService;
import com.mursalin.SCMS.utils.UserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final UserUtil userUtil;
    private final ComplaintRepository complaintRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(UserUtil userUtil, ComplaintRepository complaintRepository, CommentRepository commentRepository) {
        this.userUtil = userUtil;
        this.complaintRepository = complaintRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void addComment(String userEmail, CommentDTO commentDto, Long complaintId) {
        User user = userUtil.getUserFromDB(userEmail);
        Complaint complaint = complaintRepository.findComplaintByIdAndUserEmail(complaintId, userEmail)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found"));

        Comment comment = Comment.builder()
                .createdAt(LocalDateTime.now())
                .comment(commentDto.getComment())
                .commentedBy(user.getUserName())
                .complaint(complaint)
                .build();

        complaint.setComments(comment);

        commentRepository.save(comment);

    }

    @Override
    @Transactional
    public void updateComment(String userEmail, CommentDTO commentDto, Long complaintId) {

        Complaint complaint = complaintRepository.findComplaintByIdAndUserEmail(complaintId, userEmail)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found"));

        Comment comment = commentRepository.findById(commentDto.getCommentId())
                .orElseThrow(() -> new CustomException("Comment not found", HttpStatus.NOT_FOUND));

        if(!comment.getComplaint().getComplaintId().equals(complaintId)) {
            throw new UnauthorizedActionException("You are not authorized to update this comment");
        }

        comment.setComment(commentDto.getComment());
        comment.setEditedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }
}
