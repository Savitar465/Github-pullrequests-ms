package com.githubx.githubpullrequestms.model.enums;

public enum PrStatus {
    OPEN("open"),
    CLOSED("closed"),
    MERGED("merged");

    private final String value;

    PrStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PrStatus fromValue(String value) {
        for (PrStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown PrStatus: " + value);
    }
}
