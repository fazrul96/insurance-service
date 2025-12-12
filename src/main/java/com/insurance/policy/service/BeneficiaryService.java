package com.insurance.policy.service;

import com.insurance.policy.data.entity.Beneficiary;
import com.insurance.policy.dto.request.BeneficiaryRequestDto;
import com.insurance.policy.dto.response.BeneficiaryListResponseDto;
import com.insurance.policy.dto.response.BeneficiaryResponseDto;

public interface BeneficiaryService {

    BeneficiaryListResponseDto getBeneficiaries(String requestId);

    BeneficiaryListResponseDto getBeneficiariesByPolicyNo(String requestId, String policyNo);

    BeneficiaryListResponseDto getBeneficiariesByPolicyId (String requestId, Long id);

    BeneficiaryResponseDto createBeneficiaries(String requestId, String userId, BeneficiaryRequestDto request);

    Beneficiary createBeneficiary(String requestId, Beneficiary request);

    String getServiceName();
}
