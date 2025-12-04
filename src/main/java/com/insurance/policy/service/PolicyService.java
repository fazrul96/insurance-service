package com.insurance.policy.service;

import com.insurance.policy.data.entity.Payment;
import com.insurance.policy.data.entity.Policy;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;

public interface PolicyService {
    QuotationApplication getQuotationApplication(Long id);
    QuotationApplicationResponseDto createApplication(
            QuotationApplicationRequestDto requestDto, String userId, String requestId
    );

    Policy createPolicy(QuotationApplication application, Payment payment);
}