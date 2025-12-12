package com.insurance.policy.util.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.insurance.policy.constants.GeneralConstant.LOG4j.REQUEST_ID;

@Slf4j
@Component
public class LogUtils {
    protected static final String INFO_LOG_PATTERN = "[" + REQUEST_ID + ": {}] Execute {}";
    protected static final String ERROR_LOG_PATTERN = "[" + REQUEST_ID + ": {}] Execute {} ERROR: {}";

    /**
     * Logs the start of the request processing, including the method name and request ID.
     *
     * @param requestId The unique request ID for tracking.
     * @param methodName The name of the method that is being executed.
     */
    public void logRequest(String requestId, String methodName) {
        log.info(INFO_LOG_PATTERN, requestId, methodName);
    }

    public void logRequest(String requestId, String methodName, Exception e) {
        log.error(ERROR_LOG_PATTERN, requestId, methodName, e.getMessage());
    }
}
