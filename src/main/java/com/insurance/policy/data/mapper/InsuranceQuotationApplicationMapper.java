package com.insurance.policy.data.mapper;

import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InsuranceQuotationApplicationMapper {
    QuotationApplicationResponseDto toResponseDto(QuotationApplication quotationApplication);
}