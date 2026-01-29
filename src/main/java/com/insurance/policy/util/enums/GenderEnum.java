package com.insurance.policy.util.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("M", "male"),
    FEMALE("F", "female");

    private final String code;
    private final String label;

    GenderEnum(String code, String label) {
        this.code = code;
        this.label = label;
    }

    /**
     * Resolves GenderEnum from code or label.
     * Accepts: M, F, male, female (case-insensitive).
     */
    public static GenderEnum from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Gender must not be null or blank");
        }

        String normalized = value.trim().toLowerCase();

        for (GenderEnum gender : values()) {
            if (gender.code.equalsIgnoreCase(normalized)
                    || gender.label.equalsIgnoreCase(normalized)) {
                return gender;
            }
        }

        throw new IllegalArgumentException("Invalid gender: " + value);
    }
}