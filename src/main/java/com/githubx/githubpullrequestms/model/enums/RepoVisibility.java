package com.githubx.githubpullrequestms.model.enums;

public enum RepoVisibility {
    PUBLIC("public"),
    PRIVATE("private");

    private final String value;

    RepoVisibility(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RepoVisibility fromValue(String value) {
        for (RepoVisibility visibility : values()) {
            if (visibility.value.equalsIgnoreCase(value)) {
                return visibility;
            }
        }
        throw new IllegalArgumentException("Unknown RepoVisibility: " + value);
    }
}
