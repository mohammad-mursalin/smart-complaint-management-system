package com.mursalin.SCMS.repository;

import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Query("SELECT new com.mursalin.SCMS.dto.ComplaintDTO(c.complaintId, c.title, c.description, c.category, c.status, " +
            "c.createdAt, c.updatedAt, c.imageName, c.imageType, c.imageData FROM Complaint c WHERE c.user.userId = :userId")
    List<ComplaintDTO> findComplaintsByUserId(long userId);

    @Query("SELECT new com.mursalin.SCMS.dto.ComplaintDTO(c.complaintId, c.title, c.description, c.category, c.status, " +
            "c.createdAt, c.updatedAt, c.imageName, c.imageType, c.imageData, " +
            "new com.mursalin.SCMS.dto.UserDTO(u.userId, u.userName, u.userEmail)) " +
            "FROM Complaint c JOIN c.user u")
    List<ComplaintDTO> findAllComplaintsWithUser();
}
