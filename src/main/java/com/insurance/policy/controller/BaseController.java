package com.insurance.policy.controller;

import com.insurance.policy.constants.GeneralConstant;
import com.insurance.policy.constants.MessageConstants;
import com.insurance.policy.dto.RequestContext;
import com.insurance.policy.dto.response.ApiResponseDto;
import com.insurance.policy.exception.WebException;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import java.util.function.Supplier;

import static com.insurance.policy.constants.GeneralConstant.LOG4j.REQUEST_ID;

/**
 * Abstract base controller to provide unified API responses and helper utilities.
 * Not meant to be instantiated directly.
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseController {
    protected static final String INFO_LOG_PATTERN = "[" + REQUEST_ID + ": {}] Execute {}";
    protected static final String ERROR_LOG_PATTERN = "[" + REQUEST_ID + ": {}] Execute {} ERROR: {}";

    /**
     * Every controller provides its own name.
     * Useful for debugging/logging, and satisfies Checkstyle.
     */
    protected abstract String getControllerName();

    /**
     * Resolves and returns the request ID. If the provided requestId is null,
     * a new requestId will be generated.
     *
     * @param requestId The request ID passed in the request parameters.
     * @return The resolved or generated request ID.
     */
    protected static String resolveRequestId(String requestId) {
        return requestId == null || requestId.isBlank()
                ? UUID.randomUUID().toString()
                : requestId;
    }


    @ModelAttribute
    protected RequestContext requestContext(
            @RequestHeader(value = "userId", required = false) String userId,
            @RequestParam(value = "prefix", required = false)
            @Parameter(name = "prefix", description = "Optional prefix added to the file key paths.",
                    example = "documents/"
            ) String prefix,
            @RequestParam(value = "language", required = false)
            @Parameter(
                    name = "language",
                    description = "Locale for response localization. Accepts en_US or in_ID."
            ) final String language,
            @RequestParam(value = "channel", required = false)
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
        RequestContext context = new RequestContext();
        context.setUserId(userId);
        context.setPrefix(prefix);
        context.setLanguage(language != null ? language : GeneralConstant.Language.IN_ID);
        context.setChannel(channel != null ? channel : "web");
        context.setRequestId(resolveRequestId(requestId));
        return context;
    }

    protected String getResponseMessage(HttpStatus httpStatus) {
        return httpStatus.getReasonPhrase();
    }

    protected <T> ApiResponseDto<T> getResponseMessage(
            String language, String channel, String requestId, HttpStatus httpStatus,
            String status, T response, String message
    ) {
        ApiResponseDto<T> apiResponse = new ApiResponseDto<>(requestId, channel, language);
        apiResponse.buildResponse(response, httpStatus, status, getResponseMessage(httpStatus));
        apiResponse.setMessage(message);
        return apiResponse;
    }

    protected <T> ApiResponseDto<T> getResponseMessage(
            String language, String channel, String requestId, HttpStatus httpStatus,
            String status, T response
    ) {
        return getResponseMessage(language, channel, requestId, httpStatus, status, response, null);
    }

    /**
     * Centralized handler to execute a service call and wrap the response in ApiResponseDto.
     * Handles logging, exception catching, and response formatting.
     *
     * @param context RequestContext containing request metadata
     * @param serviceCall Supplier with the service logic
     * @param <T> type of the response
     * @return ApiResponseDto containing the response or error
     */
    protected <T> ApiResponseDto<T> handleRequest(String methodName, RequestContext context, Supplier<T> serviceCall) {
        log.info(INFO_LOG_PATTERN, context.getRequestId(), methodName);

        try {
            T data = serviceCall.get();
            return getResponseMessage(
                    context.getLanguage(),
                    context.getChannel(),
                    context.getRequestId(),
                    HttpStatus.OK,
                    HttpStatus.OK.getReasonPhrase(),
                    data,
                    MessageConstants.HttpDescription.OK_DESC
            );
        } catch (WebException we) {
            log.error(ERROR_LOG_PATTERN, context.getRequestId(), methodName, we.getMessage());
            return getResponseMessage(
                    context.getLanguage(),
                    context.getChannel(),
                    context.getRequestId(),
                    HttpStatus.BAD_REQUEST,
                    MessageConstants.HttpDescription.BAD_REQUEST_DESC,
                    null,
                    we.getMessage()
            );
        } catch (BadCredentialsException bce) {
            log.error(ERROR_LOG_PATTERN, context.getRequestId(), methodName, bce.getMessage());
            return getResponseMessage(
                    context.getLanguage(),
                    context.getChannel(),
                    context.getRequestId(),
                    HttpStatus.UNAUTHORIZED,
                    MessageConstants.HttpDescription.UNAUTHORIZED_DESC,
                    null,
                    bce.getMessage()
            );
        } catch (Exception e) {
            log.error(ERROR_LOG_PATTERN, context.getRequestId(), methodName, e.getMessage());
            return getResponseMessage(
                    context.getLanguage(),
                    context.getChannel(),
                    context.getRequestId(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    MessageConstants.HttpDescription.INTERNAL_ERROR_DESC,
                    null,
                    e.getMessage()
            );
        }
    }
}