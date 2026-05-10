package com.githubx.githubpullrequestms.dto.response;

import com.githubx.githubpullrequestms.model.enums.PrStatus;

import java.util.UUID;

public record PullRequestResponse(
        UUID id,
        String repoId,
        Integer number,
        String title,
        String description,
        String sourceBranch,
        String targetBranch,
        AuthorSummaryResponse author,
        PrStatus status,
        Boolean hasConflicts,
        Integer commitsCount,
        String createdAt,
        String updatedAt,
        String mergedAt
) {}
