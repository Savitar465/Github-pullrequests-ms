package com.githubx.githubpullrequestms.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalRepositoryDto(
    String id,
    String name,
    String fullName,
    String description,
    String visibility,
    String ownerUsername,
    String defaultBranch
) {
}
