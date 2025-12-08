package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant.INSURANCE;
import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationSummaryResponseDto;
import com.insurance.policy.service.QuotationApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class QuotationApplicationController extends BaseController {
    private final QuotationApplicationService quotationApplicationService;

    @Operation(summary = "Retrieve all quotations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = INSURANCE.QUOTATION_LIST)
    public ApiResponseDto<QuotationApplicationSummaryResponseDto> getAllQuotations(
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
        log.info("[RequestId: {}] Starting QuotationApplicationController.getAllQuotations()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            QuotationApplicationSummaryResponseDto response = quotationApplicationService.getAllQuotations(requestId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute QuotationApplicationController.getAllQuotations() ERROR: {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Retrieve quotations by status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = INSURANCE.QUOTATION_LIST_WITH_STATUS)
    public ApiResponseDto<QuotationApplicationSummaryResponseDto> getQuotationsByStatus(
            @PathVariable("status") String status,
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
        log.info("[RequestId: {}] Starting QuotationApplicationController.getQuotationsByStatus()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            QuotationApplicationSummaryResponseDto response = quotationApplicationService.getQuotationsByStatus(requestId, status);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute QuotationApplicationController.getQuotationsByStatus() ERROR: {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Retrieve quotations by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = INSURANCE.QUOTATION_LIST_WITH_ID)
    public ApiResponseDto<Optional<QuotationApplication>> getQuotationsById(
            @PathVariable("id") Long id,
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
        log.info("[RequestId: {}] Starting QuotationApplicationController.getQuotationsById()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            Optional<QuotationApplication> response = quotationApplicationService.getQuotationsById(requestId, id);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute QuotationApplicationController.getQuotationsById() ERROR: {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Retrieve quotations by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @GetMapping(path = INSURANCE.QUOTATION_LIST_WITH_USER_ID)
    public ApiResponseDto<QuotationApplicationSummaryResponseDto> getQuotationsByUserId(
            @PathVariable("userId") String userId,
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
        log.info("[RequestId: {}] Starting QuotationApplicationController.getQuotationsByUserId()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            QuotationApplicationSummaryResponseDto response = quotationApplicationService.getQuotationsByUserId(requestId, userId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute QuotationApplicationController.getQuotationsByUserId() ERROR: {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }
}