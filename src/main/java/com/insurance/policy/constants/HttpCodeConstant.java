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

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class HttpCodes {
        public static final String OK = "200";
        public static final String CREATED = "201";
        public static final String NO_CONTENT = "204";
        public static final String BAD_REQUEST = "400";
        public static final String UNAUTHORIZED = "401";
        public static final String FORBIDDEN = "403";
        public static final String NOT_FOUND = "404";
        public static final String CONFLICT = "409";
        public static final String UNPROCESSABLE_ENTITY = "422";
        public static final String INTERNAL_SERVER_ERROR = "500";
    }
}