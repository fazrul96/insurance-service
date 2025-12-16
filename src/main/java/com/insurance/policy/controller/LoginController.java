package com.insurance.policy.controller;

import com.insurance.policy.config.swagger.DefaultApiResponses;
import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.data.entity.User;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.request.AuthRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.AuthResponseDto;
import com.insurance.policy.service.impl.auth.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.publicApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class LoginController extends BaseController {
    private final AuthServiceImpl authService;

    @Override
    protected String getControllerName() {
        return "LoginController";
    }

    @Operation(summary = "Login User")
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.INSURANCE.LOGIN)
    public ApiResponseDto<AuthResponseDto> loginUser(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(
                    name = "request",
                    description = "Payload containing username and password to login.",
                    required = true
            ) final AuthRequestDto request
    ) {
        return handleRequest(getControllerName() + "loginUser",
                context, () -> authService.login(context.getRequestId(), request));
    }

    @Operation(summary = "Login User Auth0")
    @DefaultApiResponses
    @PostMapping(path = ApiConstant.INSURANCE.LOGIN_AUTH0)
    public ApiResponseDto<AuthResponseDto> loginUserWithAuth0(
            RequestContext context,
            @Valid @RequestBody
            @Parameter(
                    name = "user",
                    description = "Payload containing username and password to login.",
                    required = true
            ) final User request
    ) {
        return handleRequest(getControllerName() + "loginUserWithAuth0",
                context, () -> authService.loginAuth0(context.getRequestId(), request));
    }
}