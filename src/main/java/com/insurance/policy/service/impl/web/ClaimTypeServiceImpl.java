package com.insurance.policy.service.impl.web;

import com.insurance.policy.data.entity.ClaimDocument;
import com.insurance.policy.data.entity.ClaimType;
import com.insurance.policy.data.repository.ClaimDocumentRepository;
import com.insurance.policy.data.repository.ClaimTypeRepository;
import com.insurance.policy.exception.WebException;
import com.insurance.policy.service.ClaimTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimTypeServiceImpl implements ClaimTypeService {
    private final ClaimTypeRepository claimTypeRepository;
    private final ClaimDocumentRepository claimDocumentRepository;

    public ClaimType getClaimTypeById(Long claimTypeId) {
        return claimTypeRepository.findById(claimTypeId)
                .orElseThrow(() -> new WebException("Claim type not found"));
    }

    public ClaimType getClaimTypeByClaimId(Long claimId) {
        return claimTypeRepository.getClaimTypeByClaimId(claimId);
    }

    public List<ClaimDocument> getClaimDocumentByClaimId(Long claimTypeId) {
        return claimDocumentRepository.getClaimDocumentByClaimId(claimTypeId);
    }
}