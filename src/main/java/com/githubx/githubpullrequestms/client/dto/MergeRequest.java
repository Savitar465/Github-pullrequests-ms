package com.githubx.githubpullrequestms.client.dto;

public record MergeRequest(
    String sourceBranch,
    String targetBranch,
    String strategy,
    String commitMessage,
    String authorName,
    String authorEmail
) {}
