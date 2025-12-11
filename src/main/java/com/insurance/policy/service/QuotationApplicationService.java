package com.insurance.policy.service;

import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationSummaryResponseDto;

public interface QuotationApplicationService {
    QuotationApplication getQuotationsById(String requestId, Long id);

    QuotationApplicationSummaryResponseDto getAllQuotations(String requestId);

    QuotationApplicationSummaryResponseDto getQuotationsByStatus(String requestId, String status);

    QuotationApplicationSummaryResponseDto getQuotationsByUserId(String requestId, String userId);

    QuotationApplication createQuotation(QuotationApplication application);

    QuotationApplicationResponseDto processQuotation(
            String requestId, String userId, QuotationApplicationRequestDto request);

    QuotationApplicationResponseDto toQuotationApplicationResponse(QuotationApplication request);
}
