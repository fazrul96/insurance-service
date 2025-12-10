package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.request.BeneficiaryRequestDto;
import com.insurance.policy.dto.response.*;
import com.insurance.policy.service.impl.web.BeneficiaryServiceImpl;
import com.insurance.policy.service.impl.web.PolicyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class PolicyController extends BaseController {
    private final PolicyServiceImpl policyService;
    private final BeneficiaryServiceImpl beneficiaryService;

    @Operation(summary = "Fetch all policies")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.GET_POLICIES)
    public ApiResponseDto<PolicySummaryResponseDto> getAllPolicies(RequestContext context) {
        logRequest(context.getRequestId(), "PolicyController.getAllPolicies()");
        return handleRequest(context, () -> policyService.getAllPolicies(context.getRequestId()));
    }

    @Operation(summary = "Fetch policy with Id")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.GET_POLICY_WITH_ID)
    public ApiResponseDto<PolicyResponseDto> getPolicyWithId(
            RequestContext context, @PathVariable Long id
    ) {
        logRequest(context.getRequestId(), "PolicyController.getPolicyWithId()");
        return handleRequest(context, () -> policyService.getPolicyById(context.getRequestId(), id));
    }

    @Operation(summary = "Create Application")
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.INSURANCE.CREATE_APPLICATION)
    public ApiResponseDto<QuotationApplicationResponseDto> createApplication(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(
                    name = "request",
                    description = "Payload containing quotation.",
                    required = true
            ) final QuotationApplicationRequestDto request
    ) {
        logRequest(context.getRequestId(), "PolicyController.createApplication()");
        return handleRequest(context, () -> policyService.createApplication(
                request, context.getUserId(), context.getRequestId()));
    }

    @Operation(summary = "Create Beneficiaries")
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.INSURANCE.CREATE_BENEFICIARIES)
    public ApiResponseDto<BeneficiaryResponseDto> upsertAllBeneficiaries(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(
                    name = "request",
                    description = "Payload containing beneficiary.",
                    required = true
            ) final BeneficiaryRequestDto request
    ) {
        logRequest(context.getRequestId(), "PolicyController.upsertAllBeneficiaries()");
        return handleRequest(context, () -> beneficiaryService.createBeneficiaries(
                context.getRequestId(), context.getUserId(), request));
    }
}