package com.insurance.policy.service.impl.web;

import com.insurance.policy.data.entity.TermsDeclaration;
import com.insurance.policy.data.repository.TermsDeclarationRepository;
import com.insurance.policy.dto.response.TermsDeclarationResponseDto;
import com.insurance.policy.service.TermsDeclarationService;
import com.insurance.policy.util.common.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermsDeclarationServiceImpl implements TermsDeclarationService {
    private final TermsDeclarationRepository termsDeclarationRepository;
    private final LogUtils logUtils;

    @Override
    public String getServiceName() {
        return "TermsDeclarationServiceImpl";
    }

    @Override
    public TermsDeclarationResponseDto getAllTerms(String requestId) {
        logUtils.logRequest(requestId, getServiceName() + "getAllTerms");

        List<TermsDeclaration> terms = termsDeclarationRepository.findAll();
        return new TermsDeclarationResponseDto(terms);
    }
}