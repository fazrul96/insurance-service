package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.QuotationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationApplicationRepository extends JpaRepository<QuotationApplication,Long> {}