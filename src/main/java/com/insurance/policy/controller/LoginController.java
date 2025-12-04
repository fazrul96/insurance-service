package com.insurance.policy.controller;

import com.insurance.policy.constants.ApiConstant;
import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.data.entity.User;
import com.insurance.policy.dto.request.AuthRequestDto;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.dto.response.LoginResponseDto;
import com.insurance.policy.service.impl.auth.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${app.privateApiPath}")
@CrossOrigin(origins = "${app.basePath}")
public class LoginController extends BaseController {
    private final AuthServiceImpl authService;

    @Operation(summary = "Login User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.INSURANCE.LOGIN)
    public ApiResponseDto<LoginResponseDto> loginUser(
        @Valid @RequestBody
        @Parameter(
                name = "request",
                description = "Payload containing username and password to login.",
                required = true
        ) final AuthRequestDto request,
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
        log.info("[RequestId: {}] Starting LoginController.loginUser()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            LoginResponseDto response = authService.login(request);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (BadCredentialsException e) {
            log.info("[RequestId: {}] Execute LoginController.loginUser() ERROR: {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.UNAUTHORIZED, MessageConstants.HttpDescription.UNAUTHORIZED_DESC, null);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute LoginController.loginUser() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }

    @Operation(summary = "Login User Auth0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = MessageConstants.HttpCodes.OK, description =  MessageConstants.HttpDescription.OK_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.BAD_REQUEST, description = MessageConstants.HttpDescription.BAD_REQUEST_DESC),
        @ApiResponse(responseCode = MessageConstants.HttpCodes.INTERNAL_SERVER_ERROR, description = MessageConstants.HttpDescription.INTERNAL_ERROR_DESC)
    })
    @PostMapping(path = ApiConstant.INSURANCE.LOGIN_AUTH0)
    public ApiResponseDto<LoginResponseDto> loginUserWithAuth0(
        @Valid @RequestBody
        @Parameter(
                name = "user",
                description = "Payload containing username and password to login.",
                required = true
        ) final User request,
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
        log.info("[RequestId: {}] Starting LoginController.loginUserWithAuth0()", requestId);

        HttpStatus httpStatus = HttpStatus.OK;

        try {
            LoginResponseDto response = authService.loginAuth0(request);
            return getResponseMessage(language, channel, requestId, httpStatus, httpStatus.getReasonPhrase(), response, MessageConstants.HttpDescription.OK_DESC);
        } catch (BadCredentialsException e) {
            log.info("[RequestId: {}] Execute LoginController.loginUserWithAuth0() ERROR: {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.UNAUTHORIZED, MessageConstants.HttpDescription.UNAUTHORIZED_DESC, null);
        } catch (Exception e) {
            log.info("[RequestId: {}] Execute LoginController.loginUserWithAuth0() ERROR {}",
                    requestId, e.getMessage());
            return getResponseMessage(language, channel, requestId, HttpStatus.INTERNAL_SERVER_ERROR, MessageConstants.HttpDescription.INTERNAL_ERROR_DESC, null);
        }
    }
}