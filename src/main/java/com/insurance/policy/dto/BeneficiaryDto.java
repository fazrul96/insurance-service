package com.insurance.policy.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryDto {
    private Long id;
    private Long policyId;
    private String beneficiaryName;
    private String relationshipToInsured;
    private Float share;
    private Action action;

    public enum Action {
        CREATE, UPDATE, DELETE
    }
}
