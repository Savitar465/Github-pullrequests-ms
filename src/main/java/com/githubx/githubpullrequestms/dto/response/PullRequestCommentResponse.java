package com.githubx.githubpullrequestms.dto.response;

import java.util.UUID;

public record PullRequestCommentResponse(
        UUID id,
        UUID pullRequestId,
        String body,
        String filePath,
        Integer lineNumber,
        AuthorSummaryResponse author,
        String createdAt,
        String updatedAt
) {}
