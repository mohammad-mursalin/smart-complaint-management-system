package com.mursalin.SCMS.repository;

import com.mursalin.SCMS.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Query("SELECT c FROM Complaint c WHERE c.user.userId = :userId")
    List<Complaint> findComplaintsByUserId(long userId);
}
