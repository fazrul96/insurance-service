package com.insurance.policy.util.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationTemplate {
    PAYMENT_SUCCESS(
            "Your payment was successful. Your policy has been created.",
            NotificationEventType.PAYMENT_SUCCESS,
            NotificationSeverity.INFO,
            "/payment/process"
    ),
    PAYMENT_FAILED(
            "Payment failed, application marked as FAILED.",
            NotificationEventType.PAYMENT_FAILED,
            NotificationSeverity.ERROR,
            "/payment/process"
    ),
    BENEFICIARY_CREATED(
            "Your beneficiary has been created.",
            NotificationEventType.BENEFICIARY_CREATED,
            NotificationSeverity.INFO,
            "/policy/beneficiary"
    ),
    QUOTATION_CREATED(
            "Your quotation has been created. Please complete the payment",
            NotificationEventType.QUOTATION_CREATED,
            NotificationSeverity.WARNING,
            "/policy/create-application"
    ),
    POLICY_UPDATED(
            "Your policy has been updated.",
            NotificationEventType.POLICY_UPDATED,
            NotificationSeverity.INFO,
            "/policy/"
    );

    public final String message;
    public final NotificationEventType eventType;
    public final NotificationSeverity severity;
    public final String pathPrefix;
}