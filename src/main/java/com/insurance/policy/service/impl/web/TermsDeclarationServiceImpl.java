package com.insurance.policy.service.impl.web;

import com.insurance.policy.data.entity.TermsDeclaration;
import com.insurance.policy.data.repository.TermsDeclarationRepository;
import com.insurance.policy.dto.response.TermsDeclarationResponseDto;
import com.insurance.policy.service.TermsDeclarationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TermsDeclarationServiceImpl implements TermsDeclarationService {
    private final TermsDeclarationRepository termsDeclarationRepository;

    public TermsDeclarationResponseDto getAllTerms(String requestId) {
        log.info("[RequestId: {}] Execute InsuranceTermsDeclarationService.getAllTerms()", requestId);

        List<TermsDeclaration> terms = termsDeclarationRepository.findAll();
        return new TermsDeclarationResponseDto(terms);
    }
}