package com.githubx.githubpullrequestms.mapper;

import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestCommentsResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.smithy.g.pullrequest.server.pullrequest.model.AuthorSummary;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestCommentBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestCommentsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.MergePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.MergeStrategy;
import com.smithy.g.pullrequest.server.pullrequest.model.PrStatus;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestCommentDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestMergeabilityDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewDecision;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewPullRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SmithyDtoMapper {

    // ── Request mappings ──────────────────────────────────────────────

    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "sourceBranch", source = "sourceBranch")
    @Mapping(target = "targetBranch", source = "targetBranch")
    CreatePullRequestRequest toCreatePullRequestRequest(CreatePullRequestBody body);

    default ReviewPullRequestRequest toReviewPullRequestRequest(ReviewPullRequestBody body) {
        if (body == null) return null;
        return new ReviewPullRequestRequest(
                mapReviewDecision(body.getDecision()),
                body.getComment()
        );
    }

    default MergePullRequestRequest toMergePullRequestRequest(MergePullRequestBody body) {
        if (body == null) return null;
        return new MergePullRequestRequest(
                mapMergeStrategy(body.getStrategy()),
                body.getCommitMessage()
        );
    }

    default CreatePullRequestCommentRequest toCreatePullRequestCommentRequest(CreatePullRequestCommentBody body) {
        if (body == null) return null;
        Integer lineNumber = body.getLineNumber() != null ? body.getLineNumber().intValue() : null;
        return new CreatePullRequestCommentRequest(
                body.getBody(),
                body.getFilePath(),
                lineNumber
        );
    }

    // ── Response mappings ─────────────────────────────────────────────

    default PullRequestDTO toPullRequestDTO(PullRequestResponse response) {
        if (response == null) return null;
        PullRequestDTO dto = new PullRequestDTO();
        dto.setId(response.id().toString());
        dto.setRepoId(response.repoId());
        dto.setNumber(BigDecimal.valueOf(response.number()));
        dto.setTitle(response.title());
        dto.setDescription(response.description());
        dto.setSourceBranch(response.sourceBranch());
        dto.setTargetBranch(response.targetBranch());
        dto.setAuthor(toAuthorSummary(response.author()));
        dto.setStatus(mapPrStatus(response.status()));
        dto.setHasConflicts(response.hasConflicts());
        dto.setCommitsCount(BigDecimal.valueOf(response.commitsCount()));
        dto.setCreatedAt(response.createdAt());
        dto.setUpdatedAt(response.updatedAt());
        dto.setMergedAt(response.mergedAt());
        return dto;
    }

    default AuthorSummary toAuthorSummary(AuthorSummaryResponse response) {
        if (response == null) return null;
        AuthorSummary summary = new AuthorSummary();
        summary.setId(response.id().toString());
        summary.setUsername(response.username());
        return summary;
    }

    default ListPullRequestsBody toListPullRequestsBody(ListPullRequestsResponse response) {
        if (response == null) return null;
        ListPullRequestsBody body = new ListPullRequestsBody();
        body.setPullRequests(toPullRequestDTOList(response.pullRequests()));
        return body;
    }

    default List<PullRequestDTO> toPullRequestDTOList(List<PullRequestResponse> responses) {
        if (responses == null) return null;
        return responses.stream().map(this::toPullRequestDTO).toList();
    }

    default PullRequestMergeabilityDTO toPullRequestMergeabilityDTO(PullRequestMergeabilityResponse response) {
        if (response == null) return null;
        PullRequestMergeabilityDTO dto = new PullRequestMergeabilityDTO();
        dto.setPrNumber(BigDecimal.valueOf(response.prNumber()));
        dto.setMergeable(response.mergeable());
        dto.setHasConflicts(response.hasConflicts());
        dto.setReason(response.reason());
        return dto;
    }

    default PullRequestCommentDTO toPullRequestCommentDTO(PullRequestCommentResponse response) {
        if (response == null) return null;
        PullRequestCommentDTO dto = new PullRequestCommentDTO();
        dto.setId(response.id().toString());
        dto.setPullRequestId(response.pullRequestId().toString());
        dto.setBody(response.body());
        dto.setFilePath(response.filePath());
        if (response.lineNumber() != null) {
            dto.setLineNumber(BigDecimal.valueOf(response.lineNumber()));
        }
        dto.setAuthor(toAuthorSummary(response.author()));
        dto.setCreatedAt(response.createdAt());
        dto.setUpdatedAt(response.updatedAt());
        return dto;
    }

    default ListPullRequestCommentsBody toListPullRequestCommentsBody(ListPullRequestCommentsResponse response) {
        if (response == null) return null;
        ListPullRequestCommentsBody body = new ListPullRequestCommentsBody();
        body.setComments(toPullRequestCommentDTOList(response.comments()));
        return body;
    }

    default List<PullRequestCommentDTO> toPullRequestCommentDTOList(List<PullRequestCommentResponse> responses) {
        if (responses == null) return null;
        return responses.stream().map(this::toPullRequestCommentDTO).toList();
    }

    // ── Enum mappings ─────────────────────────────────────────────────

    default com.githubx.githubpullrequestms.model.enums.PrStatus mapSmithyPrStatus(PrStatus status) {
        if (status == null) return null;
        return switch (status) {
            case OPEN -> com.githubx.githubpullrequestms.model.enums.PrStatus.OPEN;
            case CLOSED -> com.githubx.githubpullrequestms.model.enums.PrStatus.CLOSED;
            case MERGED -> com.githubx.githubpullrequestms.model.enums.PrStatus.MERGED;
        };
    }

    default PrStatus mapPrStatus(com.githubx.githubpullrequestms.model.enums.PrStatus status) {
        if (status == null) return null;
        return switch (status) {
            case OPEN -> PrStatus.OPEN;
            case CLOSED -> PrStatus.CLOSED;
            case MERGED -> PrStatus.MERGED;
        };
    }

    default com.githubx.githubpullrequestms.model.enums.ReviewDecision mapReviewDecision(ReviewDecision decision) {
        if (decision == null) return null;
        return switch (decision) {
            case APPROVED -> com.githubx.githubpullrequestms.model.enums.ReviewDecision.APPROVED;
            case CHANGES_REQUESTED -> com.githubx.githubpullrequestms.model.enums.ReviewDecision.CHANGES_REQUESTED;
            case COMMENTED -> com.githubx.githubpullrequestms.model.enums.ReviewDecision.COMMENTED;
        };
    }

    default com.githubx.githubpullrequestms.model.enums.MergeStrategy mapMergeStrategy(MergeStrategy strategy) {
        if (strategy == null) return null;
        return switch (strategy) {
            case MERGE -> com.githubx.githubpullrequestms.model.enums.MergeStrategy.MERGE;
            case SQUASH -> com.githubx.githubpullrequestms.model.enums.MergeStrategy.SQUASH;
            case REBASE -> com.githubx.githubpullrequestms.model.enums.MergeStrategy.REBASE;
        };
    }
}
