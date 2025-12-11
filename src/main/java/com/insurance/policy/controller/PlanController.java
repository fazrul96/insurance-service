package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant.INSURANCE;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.request.PlanRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.PlanResponseDto;
import com.insurance.policy.service.impl.web.PlanServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class PlanController extends BaseController {
    private final PlanServiceImpl planServiceImpl;

    @Override
    protected String getControllerName() {
        return "PlanController";
    }

    /**
     * Generate insurance quotation plans based on customer inputs.
     *
     * @param request    The plan request payload.
     * @return           APIResponse wrapper containing plan response details.
     */
    @Operation(
            summary = "Generate insurance quotation plans",
            description = "Creates insurance plan recommendations based on the provided customer and product data."
    )
    @DefaultApiResponses
    @PostMapping(path = INSURANCE.QUOTATION_PLAN)
    public ApiResponseDto<PlanResponseDto> generateQuotationPlans(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(
                    name = "request",
                    description = "Payload containing customer and product details " +
                            "to generate insurance quotation plan.",
                    required = true
            ) final PlanRequestDto request
    ) {
        logRequest(context.getRequestId(), "PlanController.generateQuotationPlans()");
        return handleRequest(context, () -> planServiceImpl.generatePlan(request, context.getRequestId()));
    }
}
