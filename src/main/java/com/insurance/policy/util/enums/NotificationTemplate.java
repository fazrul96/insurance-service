package com.insurance.policy.util.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationTemplate {
    PAYMENT_SUCCESS(
            "Your payment was successful. Your policy has been created.",
            NotificationEventType.PAYMENT_SUCCESS,
            NotificationSeverity.INFO,
            "/policy/"
    ),
    PAYMENT_FAILED(
            "Payment failed, application marked as FAILED.",
            NotificationEventType.PAYMENT_FAILED,
            NotificationSeverity.ERROR,
            "/policy/"
    ),
    BENEFICIARY_CREATED(
            "Your beneficiary has been created.",
            NotificationEventType.BENEFICIARY_CREATED,
            NotificationSeverity.INFO,
            "/beneficiary/"
    );

    public final String message;
    public final NotificationEventType eventType;
    public final NotificationSeverity severity;
    public final String pathPrefix;
}