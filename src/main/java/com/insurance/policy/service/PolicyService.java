package com.insurance.policy.service;

import com.insurance.policy.data.entity.Policy;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.response.PolicyResponseDto;
import com.insurance.policy.dto.response.PolicySummaryResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;

public interface PolicyService {

    PolicySummaryResponseDto getAllPolicies(String requestId);

    PolicySummaryResponseDto getPolicyByUserId(Long userId, String requestId);

    PolicySummaryResponseDto getPolicyByUserKey(String requestId, String userId);

    PolicyResponseDto getPolicyById(String requestId, Long id);

    PolicyResponseDto constructApplicationAndBeneficiaryResponse(Policy policy);

    QuotationApplicationResponseDto createApplication(
            QuotationApplicationRequestDto requestDto, String userId, String requestId);
}
