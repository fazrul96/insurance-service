package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.insurance.policy.constants.GeneralConstant.LOG4j.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class BeneficiaryController extends BaseController {
    private final BeneficiaryServiceImpl beneficiaryService;

    /**
     * Retrieves all beneficiaries from the system.
     *
     * @param language The language to be used for the response (default: IN_ID).
     * @param channel The channel through which the request was made (default: web).
     * @param requestId A unique request identifier for tracing.
     * @return An API response containing a list of beneficiaries.
     */
    @Operation(summary = "Retrieve all beneficiaries")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiaries(
        @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
        @Parameter(
                name = "language",
                description = "Locale for response localization. Accepts en_US or in_ID."
        ) final String language,
        @RequestParam(value = "channel", required = false, defaultValue = "web")
        @Parameter(
                name = "channel",
                description = "Source of request such as web or mobile.",
                example = "web"
        ) final String channel,
        @RequestParam(value = "requestId", required = false)
        @Parameter(
                name = "requestId",
                description = "Unique identifier per request. Auto-generated if missing.",
                example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
        ) String requestId
    ) {
        requestId = resolveRequestId(requestId);
        logRequest(requestId, BENEFICIARY_CONTROLLER_GET_BENEFICIARIES);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            BeneficiaryListResponseDto response = beneficiaryService.getBeneficiaries(requestId);
            return getResponseMessage(language, channel, requestId, HttpStatus.OK, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            logRequest(requestId, BENEFICIARY_CONTROLLER_GET_BENEFICIARIES, e);
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    /**
     * Retrieves beneficiaries by the policy number.
     *
     * @param policyNo The policy number to search for beneficiaries.
     * @param language The language for response localization (default: IN_ID).
     * @param channel  The request's originating channel (default: web).
     * @param requestId A unique identifier for this request.
     * @return An API response containing beneficiaries associated with the given policy number.
     */
    @Operation(summary = "Retrieve beneficiaries by policy number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST_WITH_POLICY_NO)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiariesByPolicyNo(
            @PathVariable("policyNo") String policyNo,
            @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
            @Parameter(
                    name = "language",
                    description = "Locale for response localization. Accepts en_US or in_ID."
            ) final String language,
            @RequestParam(value = "channel", required = false, defaultValue = "web")
            @Parameter(
                    name = "channel",
                    description = "Source of request such as web or mobile.",
                    example = "web"
            ) final String channel,
            @RequestParam(value = "requestId", required = false)
            @Parameter(
                    name = "requestId",
                    description = "Unique identifier per request. Auto-generated if missing.",
                    example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
            ) String requestId
    ) {
        requestId = resolveRequestId(requestId);
        logRequest(requestId, BENEFICIARY_CONTROLLER_GET_BENEFICIARIES_BY_POLICY_NO);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            BeneficiaryListResponseDto response = beneficiaryService.getBeneficiariesByPolicyNo(requestId, policyNo);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            logRequest(requestId, BENEFICIARY_CONTROLLER_GET_BENEFICIARIES_BY_POLICY_NO, e);
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    /**
     * Retrieves beneficiaries by the policy ID.
     *
     * @param policyId The ID of the policy to search for beneficiaries.
     * @param language The language for response localization (default: IN_ID).
     * @param channel  The request's originating channel (default: web).
     * @param requestId A unique identifier for this request.
     * @return An API response containing beneficiaries associated with the given policy ID.
     */
    @Operation(summary = "Retrieve beneficiaries by policy ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST_WITH_POLICY_ID)
    public ApiResponseDto<BeneficiaryListResponseDto> getBeneficiariesByPolicyId(
            @PathVariable("policyId") Long policyId,
            @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
            @Parameter(
                    name = "language",
                    description = "Locale for response localization. Accepts en_US or in_ID."
            ) final String language,
            @RequestParam(value = "channel", required = false, defaultValue = "web")
            @Parameter(
                    name = "channel",
                    description = "Source of request such as web or mobile.",
                    example = "web"
            ) final String channel,
            @RequestParam(value = "requestId", required = false)
            @Parameter(
                    name = "requestId",
                    description = "Unique identifier per request. Auto-generated if missing.",
                    example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
            ) String requestId
    ) {
        requestId = resolveRequestId(requestId);
        logRequest(requestId, BENEFICIARY_CONTROLLER_GET_BENEFICIARIES_BY_POLICY_ID);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            BeneficiaryListResponseDto response = beneficiaryService.getBeneficiariesByPolicyId(requestId, policyId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            logRequest(requestId, BENEFICIARY_CONTROLLER_GET_BENEFICIARIES_BY_POLICY_ID, e);
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Create beneficiaries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.INSURANCE.BENEFICIARY_LIST_CREATE)
    public ApiResponseDto<BeneficiaryResponseDto> createBeneficiaries(
            @RequestHeader("userId") String userId,
            @Valid @RequestBody
            @Parameter(
                    name = "request",
                    description = "Payload containing beneficiaries.",
                    required = true
            ) final BeneficiaryRequestDto request,
            @RequestParam(value = "language", required = false, defaultValue = GeneralConstant.Language.IN_ID)
            @Parameter(
                    name = "language",
                    description = "Locale for response localization. Accepts en_US or in_ID."
            ) final String language,
            @RequestParam(value = "channel", required = false, defaultValue = "web")
            @Parameter(
                    name = "channel",
                    description = "Source of request such as web or mobile.",
                    example = "web"
            ) final String channel,
            @RequestParam(value = "requestId", required = false)
            @Parameter(
                    name = "requestId",
                    description = "Unique identifier per request. Auto-generated if missing.",
                    example = "f3a2b1c8-8c12-4b4c-93d4-123456789abc"
            ) String requestId
    ) {
        requestId = resolveRequestId(requestId);
        logRequest(requestId, BENEFICIARY_CONTROLLER_POST_BENEFICIARIES);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            BeneficiaryResponseDto response = beneficiaryService.createBeneficiaries(requestId, userId, request);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            logRequest(requestId, BENEFICIARY_CONTROLLER_POST_BENEFICIARIES, e);
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }
}