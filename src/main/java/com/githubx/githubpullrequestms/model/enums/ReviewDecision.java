package com.githubx.githubpullrequestms.model.enums;

public enum ReviewDecision {
    APPROVED("approved"),
    CHANGES_REQUESTED("changes_requested"),
    COMMENTED("commented");

    private final String value;

    ReviewDecision(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ReviewDecision fromValue(String value) {
        for (ReviewDecision decision : values()) {
            if (decision.value.equalsIgnoreCase(value)) {
                return decision;
            }
        }
        throw new IllegalArgumentException("Unknown ReviewDecision: " + value);
    }
}
