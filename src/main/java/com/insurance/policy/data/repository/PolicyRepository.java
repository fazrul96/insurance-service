package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy,Long> {
    @Query("SELECT DISTINCT p FROM Policy p WHERE p.user.userId = :userId")
    List<Policy> findByUserId(@Param("userId") String userId);
}