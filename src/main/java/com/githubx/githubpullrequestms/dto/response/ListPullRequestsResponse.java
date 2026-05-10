package com.githubx.githubpullrequestms.dto.response;

import java.util.List;

public record ListPullRequestsResponse(
        List<PullRequestResponse> pullRequests
) {}
