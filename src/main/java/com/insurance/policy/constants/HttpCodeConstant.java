package com.insurance.policy.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpCodeConstant {
    public static final int OK = HttpStatus.OK.value();
    public static final int CREATED = HttpStatus.CREATED.value();
    public static final int NO_CONTENT = HttpStatus.NO_CONTENT.value();
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
    public static final int PAYMENT_REQUIRED = HttpStatus.PAYMENT_REQUIRED.value();
    public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
}