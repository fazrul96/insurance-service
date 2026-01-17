package com.insurance.policy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimTypeResponseDto {
    private Long claimTypeId;
    private String claimTypeName;
    private String claimTypeDescription;
}
