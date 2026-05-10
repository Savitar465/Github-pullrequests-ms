package com.githubx.githubpullrequestms.model.enums;

public enum MergeStrategy {
    MERGE("merge"),
    SQUASH("squash"),
    REBASE("rebase");

    private final String value;

    MergeStrategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MergeStrategy fromValue(String value) {
        for (MergeStrategy strategy : values()) {
            if (strategy.value.equalsIgnoreCase(value)) {
                return strategy;
            }
        }
        throw new IllegalArgumentException("Unknown MergeStrategy: " + value);
    }
}
