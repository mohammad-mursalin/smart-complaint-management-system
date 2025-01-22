package com.mursalin.SCMS.repository;

import com.mursalin.SCMS.dto.ComplaintDTO;
import com.mursalin.SCMS.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Query("SELECT c FROM Complaint c LEFT JOIN FETCH c.comments cm WHERE c.user.userId = :userId")
    List<Complaint> findComplaintsByUserId(Long userId);

    @Query("SELECT c FROM Complaint c WHERE c.complaintId = :complaintId AND c.user.userEmail = :userEmail")
    Optional<Complaint> findComplaintByIdAndUserEmail(Long complaintId, String userEmail);


    @Query("SELECT new com.mursalin.SCMS.dto.ComplaintDTO(c.complaintId, c.title, c.description, c.category, c.status, " +
            "c.createdAt, c.updatedAt, c.imageUrl, " +
            "new com.mursalin.SCMS.dto.UserDTO(u.userId, u.userName, u.userEmail)) " +
            "FROM Complaint c JOIN c.user u")
    List<ComplaintDTO> findAllComplaintsWithUser();
}
