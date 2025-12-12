package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant.INSURANCE;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.request.PaymentRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.PaymentResponseDto;
import com.insurance.policy.dto.response.PaymentSummaryResponseDto;
import com.insurance.policy.service.impl.web.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class PaymentController extends BaseController {
    private final PaymentServiceImpl paymentService;

    @Override
    protected String getControllerName() {
        return "PaymentController";
    }

    @Operation(summary = "Retrieve all payments")
    @DefaultApiResponses
    @GetMapping(path = INSURANCE.PAYMENT_LIST)
    public ApiResponseDto<PaymentSummaryResponseDto> getAllPayments(RequestContext context) {
        return handleRequest(getControllerName() + "getAllPayments",
                context, () -> paymentService.getAllPayments(context.getRequestId())
        );
    }

    @Operation(summary = "Retrieve payments by status")
    @DefaultApiResponses
    @GetMapping(path = INSURANCE.PAYMENT_LIST_WITH_STATUS)
    public ApiResponseDto<PaymentSummaryResponseDto> getPaymentsByStatus(
            RequestContext context, @PathVariable("status") String status
    ) {
        return handleRequest(getControllerName() + "getPaymentsByStatus",
                context, () -> paymentService.getPaymentsByStatus(context.getRequestId(), status)
        );
    }

    @Operation(summary = "Initiate insurance payment")
    @DefaultApiResponses
    @PostMapping(path = INSURANCE.PAYMENT_PROCESS)
    public ApiResponseDto<PaymentResponseDto> paymentProcess(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(name = "request", description = "Payload containing payment request.", required = true)
            final PaymentRequestDto request
    ) {
        return handleRequest(getControllerName() + "paymentProcess",
                context, () -> paymentService.paymentProcess(
                context.getUserId(), request, context.getRequestId())
        );
    }
}