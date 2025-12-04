package com.insurance.policy.data.repository;

import com.insurance.policy.data.entity.TermsDeclaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsDeclarationRepository extends JpaRepository<TermsDeclaration,Long> {
}