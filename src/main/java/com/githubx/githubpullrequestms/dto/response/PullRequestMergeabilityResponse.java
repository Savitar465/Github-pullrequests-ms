package com.githubx.githubpullrequestms.dto.response;

public record PullRequestMergeabilityResponse(
        Integer prNumber,
        Boolean mergeable,
        Boolean hasConflicts,
        String reason
) {}
