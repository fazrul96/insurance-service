package com.insurance.policy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClaimInfoResponse {
    private List<Map<String,String>> policyInfo;
    private List<ClaimPolicyDocument> claimPolicyDocument;

    @Data
    public static class ClaimPolicyDocument {
        private Long claimTypeId;
        private String claimTypeName;
        private String claimDescription;
        private List<String> requiredDocuments;
    }
}