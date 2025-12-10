package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.TermsDeclarationResponseDto;
import com.insurance.policy.service.impl.web.TermsDeclarationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class TermsDeclarationController extends BaseController {
    private final TermsDeclarationServiceImpl termsDeclarationService;

    @Operation(summary = "Retrieve all active insurance terms and declarations")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.GET_TERMS)
    public ApiResponseDto<TermsDeclarationResponseDto> getActiveTerms(RequestContext context) {
        logRequest(context.getRequestId(), "TermsDeclarationController.getActiveTerms()");
        return handleRequest(context, () -> termsDeclarationService.getAllTerms(context.getRequestId()));
    }
}