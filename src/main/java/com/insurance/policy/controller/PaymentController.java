package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.dto.request.PaymentRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.PaymentResponseDto;
import com.insurance.policy.service.impl.web.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class PaymentController extends BaseController {
    private final PaymentServiceImpl paymentService;

    @Operation(summary = "Initiate insurance payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
            @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.INSURANCE.PAYMENT)
    public ApiResponseDto<PaymentResponseDto> handlePayment(
        @RequestHeader("userId") String userId,
        @Valid @RequestBody
        @Parameter(
                name = "request",
                description = "Payload containing payment request.",
                required = true
        ) final PaymentRequestDto request,
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
        log.info("[RequestId: {}] Starting PaymentController.handlePayment()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            PaymentResponseDto response = paymentService.handlePayment(userId, request);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute LoginController.loginUser() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }
}