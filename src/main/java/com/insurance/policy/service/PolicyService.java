package com.insurance.policy.service;

import com.insurance.policy.data.entity.Policy;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.response.PolicyResponseDto;
import com.insurance.policy.dto.response.PolicySummaryResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;
import com.insurance.policy.exception.WebException;

public interface PolicyService {
    PolicySummaryResponseDto getAllPolicies(String requestId);
    PolicySummaryResponseDto getPolicyByUserId(Long userId, String requestId);
    PolicySummaryResponseDto getPolicyByUserKey(String requestId, String userId) throws WebException;
    PolicyResponseDto getPolicyById(String requestId, Long id) throws WebException;

    PolicyResponseDto constructApplicationAndBeneficiaryResponse(Policy policy);

    QuotationApplicationResponseDto createApplication(QuotationApplicationRequestDto requestDto, String userId, String requestId) throws WebException;
}