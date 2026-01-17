package com.insurance.policy.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiDocDescriptionsConstant {
    public static final String OK = "Request successful";
    public static final String CREATED = "Successfully created";
    public static final String BAD_REQUEST = "Invalid request input";
    public static final String UNAUTHORIZED = "Unauthorized access";
    public static final String FORBIDDEN = "Access forbidden";
    public static final String NOT_FOUND = "Resource not found";
    public static final String CONFLICT = "Conflict occurred";
    public static final String UNPROCESSABLE_ENTITY = "Unprocessable input";
    public static final String INTERNAL_ERROR = "Unexpected server error";
}