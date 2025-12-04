package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant.INSURANCE;
import com.insurance.policy.constants.GeneralConstant.Language;
import com.insurance.policy.constants.MessageConstants.HttpCodes;
import com.insurance.policy.constants.MessageConstants.HttpDescription;
import com.insurance.policy.dto.request.PlanRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.PlanResponseDto;
import com.insurance.policy.service.impl.web.PlanServiceImpl;
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
public class PlanController extends BaseController {
    private final PlanServiceImpl planServiceImpl;

    /**
     * Generate insurance quotation plans based on customer inputs.
     *
     * @param request    The plan request payload.
     * @param language   Optional language code (I18N). Supported: en_US, in_ID.
     * @param channel    Source channel such as web or mobile.
     * @param requestId  Optional client request ID for tracking. Auto-generated when absent.
     * @return           APIResponse wrapper containing plan response details.
     */
    @Operation(
            summary = "Generate insurance quotation plans",
            description = "Creates insurance plan recommendations based on the provided customer and product data."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = HttpCodes.OK, description =  HttpDescription.OK_DESC),
        @ApiResponse(responseCode = HttpCodes.BAD_REQUEST, description = HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = HttpCodes.INTERNAL_SERVER_ERROR, description = HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = INSURANCE.QUOTATION_PLAN)
    public ApiResponseDto<PlanResponseDto> generateQuotationPlans(
        @Valid @RequestBody
        @Parameter(
                name = "request",
                description = "Payload containing customer and product details to generate insurance quotation plan.",
                required = true
        ) final PlanRequestDto request,
        @RequestParam(value = "language", required = false, defaultValue = Language.IN_ID)
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
        log.info("[RequestId: {}] Starting PlanController.generateQuotationPlans()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            PlanResponseDto response = planServiceImpl.generatePlan(request, requestId);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute PlanController.generateQuotationPlans() ERROR: {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }
}
