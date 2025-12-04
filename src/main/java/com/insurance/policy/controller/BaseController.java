package com.insurance.policy.controller;

import com.insurance.policy.dto.response.ApiResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * Abstract base controller to provide unified API responses and helper utilities.
 * Not meant to be instantiated directly.
 */
@NoArgsConstructor
public abstract class BaseController {
    protected String getResponseMessage(HttpStatus httpStatus) {
        return httpStatus.getReasonPhrase();
    }

    protected String resolveRequestId(String requestId) {
        return (requestId == null || requestId.isBlank())
                ? UUID.randomUUID().toString()
                : requestId;
    }

    protected <T> ApiResponseDto<T> getResponseMessage(String language, String channel, String requestId, HttpStatus httpStatus, String status, T response, String message) {
        ApiResponseDto<T> apiResponse = new ApiResponseDto<>(requestId, channel, language);
        apiResponse.buildResponse(response, httpStatus, status, getResponseMessage(httpStatus));
        apiResponse.setMessage(message);
        return apiResponse;
    }

    protected <T> ApiResponseDto<T> getResponseMessage(String language, String channel, String requestId, HttpStatus httpStatus, String status, T response) {
        return getResponseMessage(language, channel, requestId, httpStatus, status, response, null);
    }
}