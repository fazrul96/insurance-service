package com.insurance.policy.config.swagger;

import com.insurance.policy.constants.ApiDocDescriptionsConstant;
import com.insurance.policy.constants.HttpCodeConstant;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiResponses(value = {
        @ApiResponse(responseCode = HttpCodeConstant.HttpCodes.OK,
                description = ApiDocDescriptionsConstant.OK),
        @ApiResponse(responseCode = HttpCodeConstant.HttpCodes.BAD_REQUEST,
                description = ApiDocDescriptionsConstant.BAD_REQUEST),
        @ApiResponse(responseCode = HttpCodeConstant.HttpCodes.INTERNAL_SERVER_ERROR,
                description = ApiDocDescriptionsConstant.INTERNAL_ERROR)
})
public @interface DefaultApiResponses { }
