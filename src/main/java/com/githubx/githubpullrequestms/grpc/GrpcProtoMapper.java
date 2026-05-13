package com.githubx.githubpullrequestms.grpc;

import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.model.enums.MergeStrategy;
import com.githubx.githubpullrequestms.model.enums.ReviewDecision;
import com.githubx.grpc.proto.AuthorSummary;
import com.githubx.grpc.proto.PullRequestCommentDTO;
import com.githubx.grpc.proto.PullRequestDTO;
import com.githubx.grpc.proto.PullRequestMergeabilityDTO;
import org.springframework.stereotype.Component;

@Component
public class GrpcProtoMapper {

    // ─── PrStatus ─────────────────────────────────────────────

    public com.githubx.grpc.proto.PrStatus toProtoStatus(
            com.githubx.githubpullrequestms.model.enums.PrStatus status) {
        if (status == null) return com.githubx.grpc.proto.PrStatus.PR_STATUS_UNSPECIFIED;
        return switch (status) {
            case OPEN   -> com.githubx.grpc.proto.PrStatus.PR_STATUS_OPEN;
            case CLOSED -> com.githubx.grpc.proto.PrStatus.PR_STATUS_CLOSED;
            case MERGED -> com.githubx.grpc.proto.PrStatus.PR_STATUS_MERGED;
        };
    }

    public com.githubx.githubpullrequestms.model.enums.PrStatus fromProtoStatus(
            com.githubx.grpc.proto.PrStatus status) {
        return switch (status) {
            case PR_STATUS_CLOSED -> com.githubx.githubpullrequestms.model.enums.PrStatus.CLOSED;
            case PR_STATUS_MERGED -> com.githubx.githubpullrequestms.model.enums.PrStatus.MERGED;
            default               -> com.githubx.githubpullrequestms.model.enums.PrStatus.OPEN;
        };
    }

    // ─── ReviewDecision ───────────────────────────────────────

    public ReviewDecision fromProtoReviewDecision(com.githubx.grpc.proto.ReviewDecision decision) {
        return switch (decision) {
            case REVIEW_DECISION_APPROVED           -> ReviewDecision.APPROVED;
            case REVIEW_DECISION_CHANGES_REQUESTED  -> ReviewDecision.CHANGES_REQUESTED;
            default                                 -> ReviewDecision.COMMENTED;
        };
    }

    // ─── MergeStrategy ────────────────────────────────────────

    public MergeStrategy fromProtoMergeStrategy(com.githubx.grpc.proto.MergeStrategy strategy) {
        return switch (strategy) {
            case MERGE_STRATEGY_SQUASH -> MergeStrategy.SQUASH;
            case MERGE_STRATEGY_REBASE -> MergeStrategy.REBASE;
            default                    -> MergeStrategy.MERGE;
        };
    }

    // ─── AuthorSummary ────────────────────────────────────────

    public AuthorSummary toProtoAuthor(AuthorSummaryResponse author) {
        if (author == null) return AuthorSummary.getDefaultInstance();
        return AuthorSummary.newBuilder()
                .setId(safe(author.id() != null ? author.id().toString() : null))
                .setUsername(safe(author.username()))
                .build();
    }

    // ─── PullRequestDTO ───────────────────────────────────────

    public PullRequestDTO toProto(PullRequestResponse dto) {
        return PullRequestDTO.newBuilder()
                .setId(safe(dto.id() != null ? dto.id().toString() : null))
                .setRepoId(safe(dto.repoId()))
                .setNumber(safeInt(dto.number()))
                .setTitle(safe(dto.title()))
                .setDescription(safe(dto.description()))
                .setSourceBranch(safe(dto.sourceBranch()))
                .setTargetBranch(safe(dto.targetBranch()))
                .setAuthor(toProtoAuthor(dto.author()))
                .setStatus(toProtoStatus(dto.status()))
                .setHasConflicts(dto.hasConflicts() != null && dto.hasConflicts())
                .setCommitsCount(safeInt(dto.commitsCount()))
                .setCreatedAt(safe(dto.createdAt()))
                .setUpdatedAt(safe(dto.updatedAt()))
                .setMergedAt(safe(dto.mergedAt()))
                .build();
    }

    // ─── PullRequestCommentDTO ────────────────────────────────

    public PullRequestCommentDTO toProtoComment(PullRequestCommentResponse dto) {
        return PullRequestCommentDTO.newBuilder()
                .setId(safe(dto.id() != null ? dto.id().toString() : null))
                .setPullRequestId(safe(dto.pullRequestId() != null ? dto.pullRequestId().toString() : null))
                .setBody(safe(dto.body()))
                .setFilePath(safe(dto.filePath()))
                .setLineNumber(safeInt(dto.lineNumber()))
                .setAuthor(toProtoAuthor(dto.author()))
                .setCreatedAt(safe(dto.createdAt()))
                .setUpdatedAt(safe(dto.updatedAt()))
                .build();
    }

    // ─── PullRequestMergeabilityDTO ───────────────────────────

    public PullRequestMergeabilityDTO toProtoMergeability(PullRequestMergeabilityResponse dto) {
        return PullRequestMergeabilityDTO.newBuilder()
                .setPrNumber(safeInt(dto.prNumber()))
                .setMergeable(dto.mergeable() != null && dto.mergeable())
                .setHasConflicts(dto.hasConflicts() != null && dto.hasConflicts())
                .setReason(safe(dto.reason()))
                .build();
    }

    // ─── Helpers ──────────────────────────────────────────────

    private String safe(String s) {
        return s != null ? s : "";
    }

    private int safeInt(Integer i) {
        return i != null ? i : 0;
    }
}
