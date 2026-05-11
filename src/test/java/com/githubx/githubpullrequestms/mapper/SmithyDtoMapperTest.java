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
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import com.githubx.githubpullrequestms.model.enums.MergeStrategy;
import com.githubx.githubpullrequestms.model.enums.ReviewDecision;
import com.smithy.g.pullrequest.server.pullrequest.model.AuthorSummary;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestCommentBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestCommentsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.MergePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestCommentDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestMergeabilityDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewPullRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SmithyDtoMapperTest {

    private SmithyDtoMapper mapper;
    private UUID testId;
    private AuthorSummaryResponse authorSummary;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SmithyDtoMapper.class);
        testId = UUID.randomUUID();
        authorSummary = new AuthorSummaryResponse(testId, "testuser");
    }

    // ── Request mapping tests ──────────────────────────────────────────

    @Test
    void debeMapearCreatePullRequestBody() {
        CreatePullRequestBody body = new CreatePullRequestBody();
        body.setTitle("Test PR");
        body.setDescription("Description");
        body.setSourceBranch("feature");
        body.setTargetBranch("main");

        CreatePullRequestRequest result = mapper.toCreatePullRequestRequest(body);

        assertNotNull(result);
        assertEquals("Test PR", result.title());
        assertEquals("Description", result.description());
        assertEquals("feature", result.sourceBranch());
        assertEquals("main", result.targetBranch());
    }

    @Test
    void debeRetornarNullParaCreatePullRequestBodyNull() {
        assertNull(mapper.toCreatePullRequestRequest(null));
    }

    @Test
    void debeMapearReviewPullRequestBody() {
        ReviewPullRequestBody body = new ReviewPullRequestBody();
        body.setDecision(com.smithy.g.pullrequest.server.pullrequest.model.ReviewDecision.APPROVED);
        body.setComment("LGTM");

        ReviewPullRequestRequest result = mapper.toReviewPullRequestRequest(body);

        assertNotNull(result);
        assertEquals(ReviewDecision.APPROVED, result.decision());
        assertEquals("LGTM", result.comment());
    }

    @Test
    void debeRetornarNullParaReviewPullRequestBodyNull() {
        assertNull(mapper.toReviewPullRequestRequest(null));
    }

    @Test
    void debeMapearMergePullRequestBody() {
        MergePullRequestBody body = new MergePullRequestBody();
        body.setStrategy(com.smithy.g.pullrequest.server.pullrequest.model.MergeStrategy.SQUASH);
        body.setCommitMessage("Merge commit");

        MergePullRequestRequest result = mapper.toMergePullRequestRequest(body);

        assertNotNull(result);
        assertEquals(MergeStrategy.SQUASH, result.strategy());
        assertEquals("Merge commit", result.commitMessage());
    }

    @Test
    void debeRetornarNullParaMergePullRequestBodyNull() {
        assertNull(mapper.toMergePullRequestRequest(null));
    }

    @Test
    void debeMapearCreatePullRequestCommentBody() {
        CreatePullRequestCommentBody body = new CreatePullRequestCommentBody();
        body.setBody("Great code!");
        body.setFilePath("src/main.java");
        body.setLineNumber(BigDecimal.valueOf(10));

        CreatePullRequestCommentRequest result = mapper.toCreatePullRequestCommentRequest(body);

        assertNotNull(result);
        assertEquals("Great code!", result.body());
        assertEquals("src/main.java", result.filePath());
        assertEquals(10, result.lineNumber());
    }

    @Test
    void debeMapearCreatePullRequestCommentBodySinLineNumber() {
        CreatePullRequestCommentBody body = new CreatePullRequestCommentBody();
        body.setBody("Comment");
        body.setFilePath(null);
        body.setLineNumber(null);

        CreatePullRequestCommentRequest result = mapper.toCreatePullRequestCommentRequest(body);

        assertNotNull(result);
        assertNull(result.lineNumber());
    }

    @Test
    void debeRetornarNullParaCreatePullRequestCommentBodyNull() {
        assertNull(mapper.toCreatePullRequestCommentRequest(null));
    }

    // ── Response mapping tests ─────────────────────────────────────────

    @Test
    void debeMapearPullRequestResponseADTO() {
        PullRequestResponse response = new PullRequestResponse(
                testId, "1", 1, "Test PR", "Description",
                "feature", "main", authorSummary, PrStatus.OPEN,
                false, 5, "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null);

        PullRequestDTO result = mapper.toPullRequestDTO(response);

        assertNotNull(result);
        assertEquals(testId.toString(), result.getId());
        assertEquals("1", result.getRepoId());
        assertEquals(BigDecimal.valueOf(1), result.getNumber());
        assertEquals("Test PR", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals("feature", result.getSourceBranch());
        assertEquals("main", result.getTargetBranch());
        assertNotNull(result.getAuthor());
        assertEquals(com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.OPEN, result.getStatus());
        assertFalse(result.getHasConflicts());
        assertEquals(BigDecimal.valueOf(5), result.getCommitsCount());
    }

    @Test
    void debeRetornarNullParaPullRequestResponseNull() {
        assertNull(mapper.toPullRequestDTO(null));
    }

    @Test
    void debeMapearAuthorSummaryResponse() {
        AuthorSummary result = mapper.toAuthorSummary(authorSummary);

        assertNotNull(result);
        assertEquals(testId.toString(), result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void debeRetornarNullParaAuthorSummaryResponseNull() {
        assertNull(mapper.toAuthorSummary(null));
    }

    @Test
    void debeMapearListPullRequestsResponse() {
        PullRequestResponse prResponse = new PullRequestResponse(
                testId, "1", 1, "Test PR", "Description",
                "feature", "main", authorSummary, PrStatus.OPEN,
                false, 1, "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null);
        ListPullRequestsResponse response = new ListPullRequestsResponse(List.of(prResponse));

        ListPullRequestsBody result = mapper.toListPullRequestsBody(response);

        assertNotNull(result);
        assertNotNull(result.getPullRequests());
        assertEquals(1, result.getPullRequests().size());
    }

    @Test
    void debeRetornarNullParaListPullRequestsResponseNull() {
        assertNull(mapper.toListPullRequestsBody(null));
    }

    @Test
    void debeMapearPullRequestDTOListVacia() {
        List<PullRequestDTO> result = mapper.toPullRequestDTOList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void debeRetornarNullParaPullRequestDTOListNull() {
        assertNull(mapper.toPullRequestDTOList(null));
    }

    @Test
    void debeMapearPullRequestMergeabilityResponse() {
        PullRequestMergeabilityResponse response = new PullRequestMergeabilityResponse(
                1, true, false, null);

        PullRequestMergeabilityDTO result = mapper.toPullRequestMergeabilityDTO(response);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1), result.getPrNumber());
        assertTrue(result.getMergeable());
        assertFalse(result.getHasConflicts());
        assertNull(result.getReason());
    }

    @Test
    void debeMapearPullRequestMergeabilityResponseConReason() {
        PullRequestMergeabilityResponse response = new PullRequestMergeabilityResponse(
                1, false, true, "Has conflicts");

        PullRequestMergeabilityDTO result = mapper.toPullRequestMergeabilityDTO(response);

        assertNotNull(result);
        assertFalse(result.getMergeable());
        assertTrue(result.getHasConflicts());
        assertEquals("Has conflicts", result.getReason());
    }

    @Test
    void debeRetornarNullParaPullRequestMergeabilityResponseNull() {
        assertNull(mapper.toPullRequestMergeabilityDTO(null));
    }

    @Test
    void debeMapearPullRequestCommentResponse() {
        UUID prId = UUID.randomUUID();
        PullRequestCommentResponse response = new PullRequestCommentResponse(
                testId, prId, "Comment body", "src/main.java", 10,
                authorSummary, "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z");

        PullRequestCommentDTO result = mapper.toPullRequestCommentDTO(response);

        assertNotNull(result);
        assertEquals(testId.toString(), result.getId());
        assertEquals(prId.toString(), result.getPullRequestId());
        assertEquals("Comment body", result.getBody());
        assertEquals("src/main.java", result.getFilePath());
        assertEquals(BigDecimal.valueOf(10), result.getLineNumber());
        assertNotNull(result.getAuthor());
    }

    @Test
    void debeMapearPullRequestCommentResponseSinLineNumber() {
        UUID prId = UUID.randomUUID();
        PullRequestCommentResponse response = new PullRequestCommentResponse(
                testId, prId, "Comment body", null, null,
                authorSummary, "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z");

        PullRequestCommentDTO result = mapper.toPullRequestCommentDTO(response);

        assertNotNull(result);
        assertNull(result.getLineNumber());
    }

    @Test
    void debeRetornarNullParaPullRequestCommentResponseNull() {
        assertNull(mapper.toPullRequestCommentDTO(null));
    }

    @Test
    void debeMapearListPullRequestCommentsResponse() {
        UUID prId = UUID.randomUUID();
        PullRequestCommentResponse commentResponse = new PullRequestCommentResponse(
                testId, prId, "Comment", null, null,
                authorSummary, "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z");
        ListPullRequestCommentsResponse response = new ListPullRequestCommentsResponse(List.of(commentResponse));

        ListPullRequestCommentsBody result = mapper.toListPullRequestCommentsBody(response);

        assertNotNull(result);
        assertNotNull(result.getComments());
        assertEquals(1, result.getComments().size());
    }

    @Test
    void debeRetornarNullParaListPullRequestCommentsResponseNull() {
        assertNull(mapper.toListPullRequestCommentsBody(null));
    }

    @Test
    void debeRetornarNullParaPullRequestCommentDTOListNull() {
        assertNull(mapper.toPullRequestCommentDTOList(null));
    }

    // ── Enum mapping tests ─────────────────────────────────────────────

    @Test
    void debeMapearSmithyPrStatusOpen() {
        PrStatus result = mapper.mapSmithyPrStatus(
                com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.OPEN);
        assertEquals(PrStatus.OPEN, result);
    }

    @Test
    void debeMapearSmithyPrStatusClosed() {
        PrStatus result = mapper.mapSmithyPrStatus(
                com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.CLOSED);
        assertEquals(PrStatus.CLOSED, result);
    }

    @Test
    void debeMapearSmithyPrStatusMerged() {
        PrStatus result = mapper.mapSmithyPrStatus(
                com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.MERGED);
        assertEquals(PrStatus.MERGED, result);
    }

    @Test
    void debeRetornarNullParaSmithyPrStatusNull() {
        assertNull(mapper.mapSmithyPrStatus(null));
    }

    @Test
    void debeMapearPrStatusOpen() {
        com.smithy.g.pullrequest.server.pullrequest.model.PrStatus result = mapper.mapPrStatus(PrStatus.OPEN);
        assertEquals(com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.OPEN, result);
    }

    @Test
    void debeMapearPrStatusClosed() {
        com.smithy.g.pullrequest.server.pullrequest.model.PrStatus result = mapper.mapPrStatus(PrStatus.CLOSED);
        assertEquals(com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.CLOSED, result);
    }

    @Test
    void debeMapearPrStatusMerged() {
        com.smithy.g.pullrequest.server.pullrequest.model.PrStatus result = mapper.mapPrStatus(PrStatus.MERGED);
        assertEquals(com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.MERGED, result);
    }

    @Test
    void debeRetornarNullParaPrStatusNull() {
        assertNull(mapper.mapPrStatus(null));
    }

    @Test
    void debeMapearReviewDecisionApproved() {
        ReviewDecision result = mapper.mapReviewDecision(
                com.smithy.g.pullrequest.server.pullrequest.model.ReviewDecision.APPROVED);
        assertEquals(ReviewDecision.APPROVED, result);
    }

    @Test
    void debeMapearReviewDecisionChangesRequested() {
        ReviewDecision result = mapper.mapReviewDecision(
                com.smithy.g.pullrequest.server.pullrequest.model.ReviewDecision.CHANGES_REQUESTED);
        assertEquals(ReviewDecision.CHANGES_REQUESTED, result);
    }

    @Test
    void debeMapearReviewDecisionCommented() {
        ReviewDecision result = mapper.mapReviewDecision(
                com.smithy.g.pullrequest.server.pullrequest.model.ReviewDecision.COMMENTED);
        assertEquals(ReviewDecision.COMMENTED, result);
    }

    @Test
    void debeRetornarNullParaReviewDecisionNull() {
        assertNull(mapper.mapReviewDecision(null));
    }

    @Test
    void debeMapearMergeStrategyMerge() {
        MergeStrategy result = mapper.mapMergeStrategy(
                com.smithy.g.pullrequest.server.pullrequest.model.MergeStrategy.MERGE);
        assertEquals(MergeStrategy.MERGE, result);
    }

    @Test
    void debeMapearMergeStrategySquash() {
        MergeStrategy result = mapper.mapMergeStrategy(
                com.smithy.g.pullrequest.server.pullrequest.model.MergeStrategy.SQUASH);
        assertEquals(MergeStrategy.SQUASH, result);
    }

    @Test
    void debeMapearMergeStrategyRebase() {
        MergeStrategy result = mapper.mapMergeStrategy(
                com.smithy.g.pullrequest.server.pullrequest.model.MergeStrategy.REBASE);
        assertEquals(MergeStrategy.REBASE, result);
    }

    @Test
    void debeRetornarNullParaMergeStrategyNull() {
        assertNull(mapper.mapMergeStrategy(null));
    }
}
