package com.insurance.policy.service;

import com.insurance.policy.dto.response.ClaimInfoResponse;
import com.insurance.policy.dto.response.ClaimListResponseDto;
import com.insurance.policy.dto.response.ClaimResponseDto;
import com.insurance.policy.exception.WebException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClaimService {
    List<ClaimListResponseDto> getAllClaims(String userId);
    ClaimInfoResponse getClaimInfoByUserId(String requestId, String userId) throws WebException;
    ClaimResponseDto submitClaim(
            String requestId, String userId, Long policyId, Long claimTypeId, List<MultipartFile> files, String prefix
    ) throws WebException;

    Resource downloadByDocumentKey (String requestId, String userId, String keyName) throws WebException;
}