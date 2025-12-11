package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.ClaimType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimTypeRepository extends JpaRepository<ClaimType, Long> {

    @Query(value = """
        SELECT ct.claim_type_id,ct.claim_type_name,ct.claim_type_description, dt.document_type_name
        FROM claim_type ct
        JOIN document_type dt ON ct.claim_type_id = dt.claim_type_id
    """, nativeQuery = true)
    List<Object[]> getClaimTypesWithDocuments();

    @Query(value = """
        SELECT ct.*
        FROM claim c
        JOIN claim_type ct ON c.claim_type_id = ct.claim_type_id
        WHERE c.claim_id = :claimId
    """, nativeQuery = true)
    ClaimType getClaimTypeByClaimId(@Param("claimId") Long claimId);
}
