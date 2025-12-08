package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long> {
    List<Beneficiary> findByPolicyId(Long id);
    Optional<Beneficiary> findById(Long id);

    @Query(value = """
        SELECT DISTINCT b.*
        FROM beneficiary b
        JOIN policy p ON p.policy_id = b.policy_id
        WHERE p.policy_no = :policyNo
    """, nativeQuery = true)
    List<Beneficiary> findByPolicyNo(@Param("policyNo") String policyNo);
}