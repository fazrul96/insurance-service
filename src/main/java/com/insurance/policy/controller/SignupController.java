package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.data.entity.User;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.SignupResponseDto;
import com.insurance.policy.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class SignupController extends BaseController {
    private final AuthService authService;

    @Override
    protected String getControllerName() {
        return "SignupController";
    }

    @Operation(summary = "Register a new user")
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.INSURANCE.SIGNUP)
    public ApiResponseDto<SignupResponseDto> registerUser(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(
                    name = "user",
                    description = "Payload containing username and password to login.",
                    required = true
            ) final User request
    ) {
        log.info("[RequestId: {}] Starting SignupController.registerUser()", context.getRequestId());

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            authService.registerUser(request);
            return getResponseMessage(context.getLanguage(), context.getChannel(), context.getRequestId(), httpStatus,
                    httpStatus.getReasonPhrase(), null, MessageConstants.HttpDescription.OK_DESC);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute SignupController.registerUser() ERROR {}",
                    context.getRequestId(), e.getMessage());
            return getResponseMessage(context.getLanguage(), context.getChannel(), context.getRequestId(),
                    HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }
}