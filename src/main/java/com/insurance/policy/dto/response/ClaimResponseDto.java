package com.insurance.policy.dto.response;

import com.insurance.policy.data.entity.ClaimType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimResponseDto {
    private Long claimId;
    private String policyNo;
    private List<Map<String,String>> documentList;
    private ClaimType claimType;
    private LocalDate claimDate;
    private String claimStatus;
}
