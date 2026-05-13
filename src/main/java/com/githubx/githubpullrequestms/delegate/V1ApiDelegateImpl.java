package com.githubx.githubpullrequestms.delegate;

import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestCommentsResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.dto.response.SearchPullRequestsResponse;
import com.githubx.githubpullrequestms.mapper.SmithyDtoMapper;
import com.githubx.githubpullrequestms.service.contratos.PullRequestCommentService;
import com.githubx.githubpullrequestms.service.contratos.PullRequestService;
import com.smithy.g.pullrequest.server.pullrequest.api.V1ApiDelegate;
import com.smithy.g.pullrequest.server.pullrequest.model.ClosePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestCommentBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestCommentsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.MergePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.PrStatus;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestCommentDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestMergeabilityDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewPullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.SearchPullRequestsBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class V1ApiDelegateImpl implements V1ApiDelegate {

    private final PullRequestService pullRequestService;
    private final PullRequestCommentService pullRequestCommentService;
    private final SmithyDtoMapper smithyDtoMapper;

    @Override
    public ResponseEntity<ListPullRequestsBody> listPullRequests(
            String owner,
            String repo,
            PrStatus status) {
        com.githubx.githubpullrequestms.model.enums.PrStatus mappedStatus =
                smithyDtoMapper.mapSmithyPrStatus(status);
        ListPullRequestsResponse response = pullRequestService.listPullRequests(owner, repo, mappedStatus);
        return ResponseEntity.ok(smithyDtoMapper.toListPullRequestsBody(response));
    }

    @Override
    public ResponseEntity<SearchPullRequestsBody> searchPullRequests(
            String owner,
            String repo,
            String q,
            PrStatus status,
            BigDecimal page,
            BigDecimal perPage) {
        com.githubx.githubpullrequestms.model.enums.PrStatus mappedStatus =
                smithyDtoMapper.mapSmithyPrStatus(status);
        int pageNum = page != null ? page.intValue() : 1;
        int perPageNum = perPage != null ? perPage.intValue() : 20;
        SearchPullRequestsResponse response = pullRequestService.searchPullRequests(
                owner, repo, q, mappedStatus, pageNum, perPageNum);
        return ResponseEntity.ok(smithyDtoMapper.toSearchPullRequestsBody(response));
    }

    @Override
    public ResponseEntity<PullRequestDTO> createPullRequest(
            String owner,
            String repo,
            CreatePullRequestBody createPullRequestBody) {
        CreatePullRequestRequest request = smithyDtoMapper.toCreatePullRequestRequest(createPullRequestBody);
        String[] userInfo = getCurrentUserInfo();
        PullRequestResponse response = pullRequestService.createPullRequest(
                owner, repo, request, userInfo[0], userInfo[1]);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(smithyDtoMapper.toPullRequestDTO(response));
    }

    @Override
    public ResponseEntity<PullRequestDTO> getPullRequest(
            String owner,
            String repo,
            BigDecimal prNumber) {
        PullRequestResponse response = pullRequestService.getPullRequest(owner, repo, prNumber.intValue());
        return ResponseEntity.ok(smithyDtoMapper.toPullRequestDTO(response));
    }

    @Override
    public ResponseEntity<PullRequestDTO> closePullRequest(
            String owner,
            String repo,
            BigDecimal prNumber,
            ClosePullRequestBody closePullRequestBody) {
        String[] userInfo = getCurrentUserInfo();
        PullRequestResponse response = pullRequestService.closePullRequest(
                owner, repo, prNumber.intValue(), userInfo[0], userInfo[1]);
        return ResponseEntity.ok(smithyDtoMapper.toPullRequestDTO(response));
    }

    @Override
    public ResponseEntity<PullRequestDTO> reviewPullRequest(
            String owner,
            String repo,
            BigDecimal prNumber,
            ReviewPullRequestBody reviewPullRequestBody) {
        ReviewPullRequestRequest request = smithyDtoMapper.toReviewPullRequestRequest(reviewPullRequestBody);
        String[] userInfo = getCurrentUserInfo();
        PullRequestResponse response = pullRequestService.reviewPullRequest(
                owner, repo, prNumber.intValue(), request, userInfo[0], userInfo[1]);
        return ResponseEntity.ok(smithyDtoMapper.toPullRequestDTO(response));
    }

    @Override
    public ResponseEntity<PullRequestDTO> mergePullRequest(
            String owner,
            String repo,
            BigDecimal prNumber,
            MergePullRequestBody mergePullRequestBody) {
        MergePullRequestRequest request = smithyDtoMapper.toMergePullRequestRequest(mergePullRequestBody);
        String[] userInfo = getCurrentUserInfo();
        PullRequestResponse response = pullRequestService.mergePullRequest(
                owner, repo, prNumber.intValue(), request, userInfo[0], userInfo[1]);
        return ResponseEntity.ok(smithyDtoMapper.toPullRequestDTO(response));
    }

    @Override
    public ResponseEntity<PullRequestMergeabilityDTO> getPullRequestMergeability(
            String owner,
            String repo,
            BigDecimal prNumber) {
        PullRequestMergeabilityResponse response = pullRequestService.getPullRequestMergeability(
                owner, repo, prNumber.intValue());
        return ResponseEntity.ok(smithyDtoMapper.toPullRequestMergeabilityDTO(response));
    }

    @Override
    public ResponseEntity<ListPullRequestCommentsBody> listPullRequestComments(
            String owner,
            String repo,
            BigDecimal prNumber) {
        ListPullRequestCommentsResponse response = pullRequestCommentService.listComments(
                owner, repo, prNumber.intValue());
        return ResponseEntity.ok(smithyDtoMapper.toListPullRequestCommentsBody(response));
    }

    @Override
    public ResponseEntity<PullRequestCommentDTO> createPullRequestComment(
            String owner,
            String repo,
            BigDecimal prNumber,
            CreatePullRequestCommentBody createPullRequestCommentBody) {
        CreatePullRequestCommentRequest request = smithyDtoMapper.toCreatePullRequestCommentRequest(
                createPullRequestCommentBody);
        String[] userInfo = getCurrentUserInfo();
        PullRequestCommentResponse response = pullRequestCommentService.createComment(
                owner, repo, prNumber.intValue(), request, userInfo[0], userInfo[1]);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(smithyDtoMapper.toPullRequestCommentDTO(response));
    }

    private String[] getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String userId = jwt.getClaimAsString("sub");
            String username = jwt.getClaimAsString("preferred_username");
            if (username == null) {
                username = jwt.getClaimAsString("name");
            }
            if (username == null) {
                username = "anonymous";
            }
            return new String[]{userId, username};
        }
        return new String[]{UUID.randomUUID().toString(), "anonymous"};
    }
}
