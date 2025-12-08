package com.insurance.policy.data.mapper;

import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.PolicyServicingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InsurancePolicyMapper {
    @Mapping(target = "id", ignore = true)
    void updatePolicyQuotationApplication(@MappingTarget QuotationApplication target, PolicyServicingDto source);
}
