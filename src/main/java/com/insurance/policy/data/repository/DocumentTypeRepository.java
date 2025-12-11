package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Long> {

    @Query(value = "SELECT * FROM document_type WHERE claim_type_id = ?1", nativeQuery = true)
    List<DocumentType> findByClaimTypeId(String claimTypeId);
}
