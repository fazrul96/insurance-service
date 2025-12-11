package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.ClaimDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimDocumentRepository extends JpaRepository<ClaimDocument, Long> {

    @Query(value = "SELECT * FROM claim_document WHERE claim_id = ?1", nativeQuery = true)
    List<ClaimDocument> getClaimDocumentByClaimId(Long claimId);
}
