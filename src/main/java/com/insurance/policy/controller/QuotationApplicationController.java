package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant.INSURANCE;
import com.insurance.policy.data.entity.QuotationApplication;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.QuotationApplicationSummaryResponseDto;
import com.insurance.policy.service.QuotationApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class QuotationApplicationController extends BaseController {
    private final QuotationApplicationService quotationApplicationService;

    @Override
    protected String getControllerName() {
        return "QuotationApplicationController";
    }

    @Operation(summary = "Retrieve all quotations")
    @DefaultApiResponses
    @GetMapping(path = INSURANCE.QUOTATION_LIST)
    public ApiResponseDto<QuotationApplicationSummaryResponseDto> getAllQuotations(RequestContext context) {
        logRequest(context.getRequestId(), "QuotationApplicationController.getAllQuotations()");
        return handleRequest(context, () -> quotationApplicationService.getAllQuotations(context.getRequestId()));
    }

    @Operation(summary = "Retrieve quotations by status")
    @DefaultApiResponses
    @GetMapping(path = INSURANCE.QUOTATION_LIST_WITH_STATUS)
    public ApiResponseDto<QuotationApplicationSummaryResponseDto> getQuotationsByStatus(
            RequestContext context, @PathVariable("status") String status
    ) {
        logRequest(context.getRequestId(), "QuotationApplicationController.getQuotationsByStatus()");
        return handleRequest(context, () -> quotationApplicationService.getQuotationsByStatus(
                context.getRequestId(), status)
        );
    }

    @Operation(summary = "Retrieve quotations by id")
    @DefaultApiResponses
    @GetMapping(path = INSURANCE.QUOTATION_LIST_WITH_ID)
    public ApiResponseDto<QuotationApplication> getQuotationsById(
            RequestContext context, @PathVariable("id") Long id
    ) {
        logRequest(context.getRequestId(), "QuotationApplicationController.getQuotationsById()");
        return handleRequest(context, () -> quotationApplicationService.getQuotationsById(context.getRequestId(), id));
    }

    @Operation(summary = "Retrieve quotations by user id")
    @DefaultApiResponses
    @GetMapping(path = INSURANCE.QUOTATION_LIST_WITH_USER_ID)
    public ApiResponseDto<QuotationApplicationSummaryResponseDto> getQuotationsByUserId(
            RequestContext context, @PathVariable("userId") String userId
    ) {
        logRequest(context.getRequestId(), "QuotationApplicationController.getQuotationsByUserId()");
        return handleRequest(context, () -> quotationApplicationService.getQuotationsByUserId(
                context.getRequestId(), userId)
        );
    }
}