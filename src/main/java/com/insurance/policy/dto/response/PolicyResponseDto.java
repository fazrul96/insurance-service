package com.insurance.policy.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.insurance.policy.dto.BeneficiaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyResponseDto {
    private Long id;
    private String policyNo;
    private Date startDate;
    private Date endDate;
    private String status;
    private QuotationApplicationResponseDto quotationApplication;
    private List<BeneficiaryDto> beneficiaries;
}