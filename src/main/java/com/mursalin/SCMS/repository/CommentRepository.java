package com.mursalin.SCMS.repository;

import com.mursalin.SCMS.dto.CommentDTO;
import com.mursalin.SCMS.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT new com.mursalin.SCMS.dto.CommentDTO(c.commentId, c.comment, c.createdAt, c.editedAt, c.commentedBy) " +
            "FROM Comment c " +
            "WHERE c.complaint.complaintId = :complaintId " +
            "ORDER BY c.createdAt ASC")
    List<CommentDTO> findCommentsByComplaintId(Long complaintId);

}
