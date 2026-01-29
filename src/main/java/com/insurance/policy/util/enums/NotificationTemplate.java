package com.insurance.policy.util.enums;

import lombok.Getter;

@Getter
public enum NotificationTemplate {
    PAYMENT_SUCCESS(
            NotificationEventType.PAYMENT_SUCCESS,
            "notification.payment.success",
            NotificationSeverity.INFO,
            "/payment/process"
    ),

    PAYMENT_FAILED(
            NotificationEventType.PAYMENT_FAILED,
            "notification.payment.failed",
            NotificationSeverity.ERROR,
            "/payment/process"
    ),

    CLAIM_UPLOAD_SUCCESS(
            NotificationEventType.CLAIM_UPLOAD_SUCCESS,
            "notification.claim.upload.success",
            NotificationSeverity.INFO,
            "/claim/submit"
    ),

    BENEFICIARY_CREATED(
            NotificationEventType.BENEFICIARY_CREATED,
            "notification.beneficiary.created",
            NotificationSeverity.INFO,
            "/policy/beneficiary"
    ),

    QUOTATION_CREATED(
            NotificationEventType.QUOTATION_CREATED,
            "notification.quotation.created",
            NotificationSeverity.WARNING,
            "/policy/create-application"
    ),

    POLICY_UPDATED(
            NotificationEventType.POLICY_UPDATED,
            "notification.policy.updated",
            NotificationSeverity.INFO,
            "/policy"
    );

    private final NotificationEventType eventType;
    private final String messageKey;
    private final NotificationSeverity severity;
    private final String path;

    NotificationTemplate(
            NotificationEventType eventType,
            String messageKey,
            NotificationSeverity severity,
            String path
    ) {
        this.eventType = eventType;
        this.messageKey = messageKey;
        this.severity = severity;
        this.path = path;
    }
}