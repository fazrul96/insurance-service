package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.Beneficiary;
import com.insurance.policy.data.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Long> {
    List<Beneficiary> findByPolicy(Policy policy);
    List<Beneficiary> findByPolicyId(Long id);
}