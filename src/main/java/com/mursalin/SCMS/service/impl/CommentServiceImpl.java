package com.mursalin.SCMS.service.impl;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.model.Complaint;
import com.mursalin.SCMS.repository.ComplaintRepository;
import com.mursalin.SCMS.service.CommentService;
import com.mursalin.SCMS.utils.UserUtil;

import java.util.Optional;

public class CommentServiceImpl implements CommentService {

    private final UserUtil userUtil;
    private final ComplaintRepository complaintRepository;

    public CommentServiceImpl(UserUtil userUtil, ComplaintRepository complaintRepository) {
        this.userUtil = userUtil;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public void addComment(String userEmail, CommentDTO comment, Long complaintId) {
        Long userId = userUtil.getUserFromDB(userEmail).getUserId();
        Optional<Complaint> complaint = complaintRepository.find

    }

    @Override
    public void updateComment(String userEmail, CommentDTO comment, Long complaintId) {

    }
}
