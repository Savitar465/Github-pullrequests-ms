package com.githubx.githubpullrequestms.dto.response;

import java.util.List;

public record ListPullRequestCommentsResponse(
        List<PullRequestCommentResponse> comments
) {}
