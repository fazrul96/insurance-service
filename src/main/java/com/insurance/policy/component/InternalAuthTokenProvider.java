package com.insurance.policy.component;

public interface InternalAuthTokenProvider {
    /**
     * Returns the Authorization header dynamically.
     * Could be static or retrieved from a login service.
     */
    String getAuthorizationHeader();
}