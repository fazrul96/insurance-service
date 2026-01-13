package com.insurance.policy.service;

import com.insurance.policy.data.entity.Payment;
import com.insurance.policy.data.entity.Policy;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.PaymentDetailsDto;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.request.PaymentRequestDto;
import com.insurance.policy.dto.response.PaymentResponseDto;
import com.insurance.policy.dto.response.PolicyResponseDto;
import com.insurance.policy.dto.response.PolicySummaryResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;

public interface PolicyService {
    Policy findPolicyById(String requestId, Long id);

    PolicySummaryResponseDto getAllPolicies(String requestId);

    PolicySummaryResponseDto getPolicyByUserId(Long userId, String requestId);

    PolicySummaryResponseDto getPolicyByUserKey(String requestId, String userId);

    PolicyResponseDto getPolicyById(String requestId, Long id);

    PolicyResponseDto getPolicyDetailsById(String requestId, String userId, Long policyId);

    void updateStatusAndPayment(Long applicationId, String status, Payment payment, String requestId);

    PaymentResponseDto processPolicyPayment(
            PaymentRequestDto request, Payment payment, QuotationApplication application,
            PaymentDetailsDto paymentDetails, String requestId);

    PolicyResponseDto constructApplicationAndBeneficiaryResponse(Policy policy);

    QuotationApplicationResponseDto createApplication(
            QuotationApplicationRequestDto requestDto, String userId, String requestId);

    String getServiceName();
}
