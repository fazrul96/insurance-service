package com.insurance.policy.dto.request;

import com.insurance.policy.dto.BeneficiaryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BeneficiaryRequestDto {
    private String policyNo;
    private List<BeneficiaryDto> beneficiaries;
}