package com.insurance.policy.service;

import com.insurance.policy.data.entity.Payment;
import com.insurance.policy.data.entity.Policy;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.response.PolicyResponseDto;
import com.insurance.policy.dto.response.PolicySummaryResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;
import com.insurance.policy.exception.WebException;

public interface PolicyService {
    PolicySummaryResponseDto getAllPolicies(String requestId);
    PolicySummaryResponseDto getPolicyByUserId(Long userId, String requestId);
    PolicyResponseDto getPolicyById(String requestId, Long id) throws WebException;

    PolicyResponseDto constructPolicyResponseDto(Policy policy);

    QuotationApplicationResponseDto createApplication(QuotationApplicationRequestDto requestDto, String userId, String requestId) throws WebException;

    Policy createPolicy(QuotationApplication application, Payment payment);
}