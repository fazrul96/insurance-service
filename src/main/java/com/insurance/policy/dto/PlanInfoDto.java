package com.insurance.policy.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanInfoDto {
    @NotNull(message = "Plan ID is required.")
    private Long id;

    private String planName;
    private Double sumAssured;
    private String coverageTerm;

    @DecimalMin(value = "0.01", message = "Premium amount must be greater than 0.")
    private BigDecimal premiumAmount;

    @NotBlank(message = "Premium mode is required.")
    private String premiumMode;

    private String referenceNumber;
}