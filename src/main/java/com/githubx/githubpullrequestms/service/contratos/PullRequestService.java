package com.githubx.githubpullrequestms.service.contratos;

import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.model.enums.PrStatus;

public interface PullRequestService {

    ListPullRequestsResponse listPullRequests(String owner, String repo, PrStatus status);

    PullRequestResponse createPullRequest(String owner, String repo,
            CreatePullRequestRequest request, String currentUserId, String currentUsername);

    PullRequestResponse getPullRequest(String owner, String repo, Integer prNumber);

    PullRequestResponse reviewPullRequest(String owner, String repo, Integer prNumber,
            ReviewPullRequestRequest request, String currentUserId, String currentUsername);

    PullRequestResponse mergePullRequest(String owner, String repo, Integer prNumber,
            MergePullRequestRequest request, String currentUserId, String currentUsername);

    PullRequestMergeabilityResponse getPullRequestMergeability(String owner, String repo, Integer prNumber);
}
