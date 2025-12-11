package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long> {
    @Override
    Optional<Policy> findById(Long id);

    Optional<Policy> findByPolicyNo(String policyNo);

    List<Policy> findByUserId(Long userId);

    @Query(value = """
        SELECT DISTINCT p.*
        FROM policy p
        JOIN users u ON p.user_id = u.id
        WHERE u.user_id = :userKey
    """, nativeQuery = true)
    List<Policy> findByUserKey(@Param("userKey") String userKey);
}
