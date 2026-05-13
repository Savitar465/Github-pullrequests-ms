package com.githubx.githubpullrequestms.dto.response;

import java.util.List;

public record SearchPullRequestsResponse(
        List<PullRequestResponse> pullRequests,
        PaginationInfo pagination
) {
    public record PaginationInfo(
            int page,
            int perPage,
            long total,
            int totalPages
    ) {}
}
