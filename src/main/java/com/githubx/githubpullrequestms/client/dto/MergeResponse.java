package com.githubx.githubpullrequestms.client.dto;

public record MergeResponse(
    boolean success,
    String message,
    String mergeCommitSha,
    String strategy
) {}
