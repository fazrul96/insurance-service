package com.insurance.policy.util.enums;

import com.insurance.policy.exception.ErrorType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorType implements ErrorType {
    BAD_REQUEST(
            "ERR_400",
            "Invalid request",
            HttpStatus.BAD_REQUEST,
            ErrorCategory.VALIDATION
    ),

    UNAUTHORIZED(
            "ERR_401",
            "Unauthorized",
            HttpStatus.UNAUTHORIZED,
            ErrorCategory.AUTHENTICATION
    ),

    FORBIDDEN(
            "ERR_403",
            "Forbidden",
            HttpStatus.FORBIDDEN,
            ErrorCategory.AUTHORIZATION
    ),

    NOT_FOUND(
            "ERR_404",
            "Resource not found",
            HttpStatus.NOT_FOUND,
            ErrorCategory.BUSINESS
    ),

    INTERNAL_SERVER_ERROR(
            "ERR_500",
            "Unexpected server error",
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorCategory.SYSTEM
    );

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
    private final ErrorCategory category;

    @Override
    public String getDesc() {
        return "";
    }

    @Override
    public HttpStatus getHttpStatusCode() {
        return null;
    }
}