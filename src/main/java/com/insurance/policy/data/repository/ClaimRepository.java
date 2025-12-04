package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.Claim;
import com.insurance.policy.dto.response.ClaimListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    @Query("""
        SELECT new com.insurance.policy.dto.response.ClaimListResponseDto(
            c.claimId,
            p.id,
            p.policyNo,
            c.claimDate,
            c.claimStatus,
            ct.claimTypeName
        )
        FROM Claim c
        JOIN c.policy p
        JOIN c.claimType ct
        JOIN c.user u
        WHERE u.userId = :userId
        ORDER BY c.claimId DESC
    """)
    List<ClaimListResponseDto> findClaimListByUserId(@Param("userId") String userId);
}