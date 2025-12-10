package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.request.BeneficiaryRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.BeneficiaryListResponseDto;
import com.insurance.policy.dto.response.BeneficiaryResponseDto;
import com.insurance.policy.service.impl.web.BeneficiaryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.insurance.policy.constants.GeneralConstant.LOG4j.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class BeneficiaryController extends BaseController {
    private final BeneficiaryServiceImpl beneficiaryService;

    @Operation(summary = "Retrieve all beneficiaries")
    @DefaultApiResponses
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiaries(RequestContext context) {
        logRequest(context.getRequestId(), "BeneficiaryController.getBeneficiaries()");
        return handleRequest(context, () -> beneficiaryService.getBeneficiaries(context.getRequestId()));
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
        logRequest(context.getRequestId(), BENEFICIARY_CONTROLLER_GET_BENEFICIARIES_BY_POLICY_NO);
        return handleRequest(context, () -> beneficiaryService.getBeneficiariesByPolicyNo(
                context.getRequestId(), policyNo)
        );
    }

    /**
     * Retrieves beneficiaries by the policy ID.
     *
     * @param policyId The ID of the policy to search for beneficiaries.
     * @return An API response containing beneficiaries associated with the given policy ID.
     */
    @Operation(summary = "Retrieve beneficiaries by policy ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST_WITH_POLICY_ID)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiariesByPolicyId(
            RequestContext context, @PathVariable("policyId") Long policyId
    ) {
        logRequest(context.getRequestId(), BENEFICIARY_CONTROLLER_GET_BENEFICIARIES_BY_POLICY_ID);
        return handleRequest(context, () -> beneficiaryService.getBeneficiariesByPolicyId(
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
        logRequest(context.getRequestId(), BENEFICIARY_CONTROLLER_POST_BENEFICIARIES);
        return handleRequest(context, () -> beneficiaryService.createBeneficiaries(
                context.getRequestId(), context.getUserId() ,request)
        );
    }
}