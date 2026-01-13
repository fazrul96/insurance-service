package com.insurance.policy.service;

import com.insurance.policy.data.entity.ClaimDocument;
import com.insurance.policy.data.entity.ClaimType;

import java.util.List;

public interface ClaimTypeService {
    ClaimType getClaimTypeById(Long claimTypeId);

    ClaimType getClaimTypeByClaimId(Long claimId);

    List<ClaimDocument> getClaimDocumentByClaimId(Long claimTypeId);
}