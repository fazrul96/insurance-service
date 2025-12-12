package com.insurance.policy.service;

import com.insurance.policy.dto.response.TermsDeclarationResponseDto;

public interface TermsDeclarationService {
    TermsDeclarationResponseDto getAllTerms(String requestId);

    String getServiceName();
}