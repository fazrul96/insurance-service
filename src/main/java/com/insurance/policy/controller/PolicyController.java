package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.dto.PolicyServicingDto;
import com.insurance.policy.dto.QuotationApplicationRequestDto;
import com.insurance.policy.dto.request.BeneficiaryRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.BeneficiaryResponseDto;
import com.insurance.policy.dto.response.PolicyResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationResponseDto;
import com.insurance.policy.service.impl.web.PolicyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class PolicyController extends BaseController {
    private final PolicyServiceImpl policyService;

    @Operation(summary = "Fetch all policies")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.GET_POLICIES)
    public ApiResponseDto<List<PolicyResponseDto>> getAllPolicies(
        @RequestHeader("userId") String userId,
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
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting PolicyController.getAllPolicies()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            List<PolicyResponseDto> response = policyService.getAllPolicies(userId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute PolicyController.getAllPolicies() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Fetch policy with Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = ApiConstant.INSURANCE.GET_POLICY_WITH_ID)
    public ApiResponseDto<PolicyResponseDto> getPolicyWithId(
        @PathVariable Long id,
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
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting PolicyController.getPolicyWithId()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            PolicyResponseDto response = policyService.getPolicyById(id);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute LoginController.loginUser() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Update policy with Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PatchMapping(path = ApiConstant.INSURANCE.GET_POLICY_WITH_ID)
    public ApiResponseDto<QuotationApplicationResponseDto> updatePolicyWithId(
        @PathVariable Long id,
        @RequestHeader("userId") String userId,
        @Valid @RequestBody
        @Parameter(
                name = "request",
                description = "Payload containing policy servicing.",
                required = true
        ) final PolicyServicingDto request,
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
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting PolicyController.updatePolicyWithId()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            QuotationApplicationResponseDto response = policyService.updatePolicy(id, request, userId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute PolicyController.updatePolicyWithId() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Create Application")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.INSURANCE.CREATE_APPLICATION)
    public ApiResponseDto<QuotationApplicationResponseDto> createApplication(
        @RequestHeader("userId") String userId,
        @Valid @RequestBody
        @Parameter(
                name = "request",
                description = "Payload containing quotation.",
                required = true
        ) final QuotationApplicationRequestDto request,
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
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting PolicyController.createApplication()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            QuotationApplicationResponseDto response = policyService.createApplication(request, userId, requestId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute PolicyController.createApplication() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Create Beneficiaries")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.INSURANCE.CREATE_BENEFICIARIES)
    public ApiResponseDto<BeneficiaryResponseDto> upsertAllBeneficiaries(
        @RequestHeader("userId") String userId,
        @Valid @RequestBody
        @Parameter(
                name = "request",
                description = "Payload containing beneficiary.",
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
        requestId = this.resolveRequestId(requestId);
        log.info("[RequestId: {}] Starting PolicyController.upsertAllBeneficiaries()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            BeneficiaryResponseDto response = policyService.upsertAll(request, userId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute PolicyController.upsertAllBeneficiaries() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }
}