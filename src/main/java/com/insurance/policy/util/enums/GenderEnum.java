package com.insurance.policy.util.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("M"),
    FEMALE("F");

    private final String code;

    GenderEnum(String code) {
        this.code = code;
    }

    public static GenderEnum fromString(String value) {
        return switch (value.toLowerCase()) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> throw new IllegalArgumentException("Invalid gender: " + value);
        };
    }
}