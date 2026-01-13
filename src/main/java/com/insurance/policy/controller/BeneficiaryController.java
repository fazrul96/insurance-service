package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.request.BeneficiaryRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.BeneficiaryListResponseDto;
import com.insurance.policy.dto.response.BeneficiaryResponseDto;
import com.insurance.policy.service.BeneficiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.insurance.policy.constants.GeneralConstant.LOG4j.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class BeneficiaryController extends BaseController {
    private final BeneficiaryService beneficiaryService;

    @Override
    protected String getControllerName() {
        return "ClaimController";
    }

    @Operation(summary = "Retrieve all beneficiaries")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiaries(RequestContext context) {
        return handleRequest(getControllerName() + GET_BENEFICIARIES,
                context, () -> beneficiaryService.getBeneficiaries(context.getRequestId()));
    }

    /**
     * Retrieves beneficiaries by the policy number.
     *
     * @param policyNo The policy number to search for beneficiaries.
     * @return An API response containing beneficiaries associated with the given policy number.
     */
    @Operation(summary = "Retrieve beneficiaries by policy number")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST_WITH_POLICY_NO)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiariesByPolicyNo(
            RequestContext context, @PathVariable("policyNo") String policyNo
    ) {
        return handleRequest(getControllerName() + GET_BENEFICIARIES_BY_POLICY_NO,
                context, () -> beneficiaryService.getBeneficiariesByPolicyNo(context.getRequestId(), policyNo)
        );
    }

    /**
     * Retrieves beneficiaries by the policy ID.
     *
     * @param policyId The ID of the policy to search for beneficiaries.
     * @return An API response containing beneficiaries associated with the given policy ID.
     */
    @Operation(summary = "Retrieve beneficiaries by policy ID")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST_WITH_POLICY_ID)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiariesByPolicyId(
            RequestContext context, @PathVariable("policyId") Long policyId
    ) {
        return handleRequest(getControllerName() + GET_BENEFICIARIES_BY_POLICY_ID,
                context, () -> beneficiaryService.getBeneficiariesByPolicyId(
                context.getRequestId(), policyId)
        );
    }

    @Operation(summary = "Create beneficiaries")
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST_CREATE)
    public ApiResponseDto<BeneficiaryResponseDto> createBeneficiaries(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(
                    name = "request",
                    description = "Payload containing beneficiaries.",
                    required = true
            ) final BeneficiaryRequestDto request
    ) {
        return handleRequest(getControllerName() + CREATE_BENEFICIARIES,
                context, () -> beneficiaryService.createBeneficiaries(
                context.getRequestId(), context.getUserId() ,request)
        );
    }
}