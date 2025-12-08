package com.insurance.policy.dto.response;

import com.insurance.policy.data.entity.Beneficiary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BeneficiaryListResponseDto {
    private List<Beneficiary> beneficiaries;
}