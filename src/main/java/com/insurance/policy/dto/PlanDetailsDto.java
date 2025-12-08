package com.insurance.policy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlanDetailsDto {
    private Long id;
    private String planName;
    private Double sumAssured;
    private String coverageTerm;
    private Double monthlyPremium;
    private Double yearlyPremium;
}