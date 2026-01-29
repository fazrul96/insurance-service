package com.insurance.policy.util.enums;

import lombok.Getter;

@Getter
public enum NotificationEventType {
    // PAYMENT
    PAYMENT_SUCCESS("payment.success", Domain.PAYMENT),
    PAYMENT_FAILED("payment.failed", Domain.PAYMENT),

    // POLICY
    POLICY_CREATED("policy.created", Domain.POLICY),
    POLICY_UPDATED("policy.updated", Domain.POLICY),

    // BENEFICIARY
    BENEFICIARY_CREATED("beneficiary.created", Domain.BENEFICIARY),
    BENEFICIARY_UPDATED("beneficiary.updated", Domain.BENEFICIARY),

    // QUOTATION
    QUOTATION_CREATED("quotation.created", Domain.QUOTATION),
    QUOTATION_STATUS_CHANGED("quotation.status.changed", Domain.QUOTATION),

    // CLAIM
    CLAIM_UPLOAD_SUCCESS("claim.upload.success", Domain.CLAIM);

    private final String code;
    private final Domain domain;

    NotificationEventType(String code, Domain domain) {
        this.code = code;
        this.domain = domain;
    }

    public enum Domain {
        PAYMENT,
        POLICY,
        BENEFICIARY,
        QUOTATION,
        CLAIM
    }
}