package com.insurance.policy.util.enums;

import com.insurance.policy.exception.BaseException;
import lombok.Getter;

import static com.insurance.policy.exception.BatchErrorType.GENERIC_ERROR;

@Getter
public enum PaymentStatus {
    SUCCESS,
    FAILED;

    public static PaymentStatus fromString(String status) {
        try {
            return PaymentStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BaseException(GENERIC_ERROR);
        }
    }
}