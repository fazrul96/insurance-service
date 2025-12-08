package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long> {
    Optional<Policy> findById(Long id);
    Optional<Policy> findByPolicyNo(String policyNo);
    List<Policy> findByUserId(Long userId);
}