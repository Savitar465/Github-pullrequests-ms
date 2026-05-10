package com.githubx.githubpullrequestms.service.contratos;

import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestCommentsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;

public interface PullRequestCommentService {

    ListPullRequestCommentsResponse listComments(String owner, String repo, Integer prNumber);

    PullRequestCommentResponse createComment(String owner, String repo, Integer prNumber,
            CreatePullRequestCommentRequest request, String currentUserId, String currentUsername);
}
