package com.insurance.policy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimListResponseDto {
    private Long claimId;
    private Long policyId;
    private String policyNo;
    private LocalDate claimDate;
    private String claimStatus;
    private String claimType;
}