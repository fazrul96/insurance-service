package com.insurance.policy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuotationApplicationRequestDto {
    @NotNull(message = "Person information is required.")
    @Valid
    private PersonDto personDto;

    @NotNull(message = "Plan information is required.")
    @Valid
    private PlanInfoDto planInfoDto;
}