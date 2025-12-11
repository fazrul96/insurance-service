package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.QuotationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuotationApplicationRepository extends JpaRepository<QuotationApplication,Long> {
    @Override
    Optional<QuotationApplication> findById(Long id);

    List<QuotationApplication> findByApplicationStatus(String applicationStatus);

    @Query(value = """
        SELECT DISTINCT qa.*
        FROM quotation_application qa
        JOIN users u ON qa.user_id = u.id
        WHERE u.user_id = :userId
    """, nativeQuery = true)
    List<QuotationApplication> findByUserId(@Param("userId") String userId);
}
