package com.insurance.policy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class QuotationApplicationSummaryResponseDto {
    private List<QuotationApplicationResponseDto> quotationApplications;
}