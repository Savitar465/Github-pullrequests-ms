package com.githubx.githubpullrequestms.dto;

import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.dto.response.*;
import com.githubx.githubpullrequestms.model.enums.MergeStrategy;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import com.githubx.githubpullrequestms.model.enums.ReviewDecision;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void createPullRequestRequestDebeCrearse() {
        CreatePullRequestRequest request = new CreatePullRequestRequest(
                "Title", "Description", "feature", "main");

        assertEquals("Title", request.title());
        assertEquals("Description", request.description());
        assertEquals("feature", request.sourceBranch());
        assertEquals("main", request.targetBranch());
    }

    @Test
    void createPullRequestCommentRequestDebeCrearse() {
        CreatePullRequestCommentRequest request = new CreatePullRequestCommentRequest(
                "Comment body", "file.java", 10);

        assertEquals("Comment body", request.body());
        assertEquals("file.java", request.filePath());
        assertEquals(10, request.lineNumber());
    }

    @Test
    void createPullRequestCommentRequestSinFilePath() {
        CreatePullRequestCommentRequest request = new CreatePullRequestCommentRequest(
                "General comment", null, null);

        assertEquals("General comment", request.body());
        assertNull(request.filePath());
        assertNull(request.lineNumber());
    }

    @Test
    void mergePullRequestRequestDebeCrearse() {
        MergePullRequestRequest request = new MergePullRequestRequest(
                MergeStrategy.SQUASH, "Merge commit message");

        assertEquals("Merge commit message", request.commitMessage());
        assertEquals(MergeStrategy.SQUASH, request.strategy());
    }

    @Test
    void reviewPullRequestRequestDebeCrearse() {
        ReviewPullRequestRequest request = new ReviewPullRequestRequest(
                ReviewDecision.APPROVED, "LGTM!");

        assertEquals(ReviewDecision.APPROVED, request.decision());
        assertEquals("LGTM!", request.comment());
    }

    @Test
    void authorSummaryResponseDebeCrearse() {
        UUID id = UUID.randomUUID();
        AuthorSummaryResponse author = new AuthorSummaryResponse(id, "username");

        assertEquals(id, author.id());
        assertEquals("username", author.username());
    }

    @Test
    void pullRequestResponseDebeCrearse() {
        UUID id = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        AuthorSummaryResponse author = new AuthorSummaryResponse(authorId, "author");

        PullRequestResponse response = new PullRequestResponse(
                id, "1", 1, "Title", "Description",
                "feature", "main", author,
                PrStatus.OPEN, false, 5,
                "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null);

        assertEquals(id, response.id());
        assertEquals("1", response.repoId());
        assertEquals(1, response.number());
        assertEquals("Title", response.title());
        assertEquals("Description", response.description());
        assertEquals("feature", response.sourceBranch());
        assertEquals("main", response.targetBranch());
        assertEquals(author, response.author());
        assertEquals(PrStatus.OPEN, response.status());
        assertFalse(response.hasConflicts());
        assertEquals(5, response.commitsCount());
    }

    @Test
    void pullRequestCommentResponseDebeCrearse() {
        UUID id = UUID.randomUUID();
        UUID prId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        AuthorSummaryResponse author = new AuthorSummaryResponse(authorId, "commenter");

        PullRequestCommentResponse response = new PullRequestCommentResponse(
                id, prId, "Comment body", "file.java", 42,
                author, "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z");

        assertEquals(id, response.id());
        assertEquals(prId, response.pullRequestId());
        assertEquals("Comment body", response.body());
        assertEquals("file.java", response.filePath());
        assertEquals(42, response.lineNumber());
        assertEquals(author, response.author());
    }

    @Test
    void listPullRequestsResponseDebeCrearse() {
        List<PullRequestResponse> prs = List.of();
        ListPullRequestsResponse response = new ListPullRequestsResponse(prs);

        assertNotNull(response.pullRequests());
        assertTrue(response.pullRequests().isEmpty());
    }

    @Test
    void listPullRequestCommentsResponseDebeCrearse() {
        List<PullRequestCommentResponse> comments = List.of();
        ListPullRequestCommentsResponse response = new ListPullRequestCommentsResponse(comments);

        assertNotNull(response.comments());
        assertTrue(response.comments().isEmpty());
    }

    @Test
    void pullRequestMergeabilityResponseDebeCrearse() {
        PullRequestMergeabilityResponse response = new PullRequestMergeabilityResponse(
                1, true, false, null);

        assertEquals(1, response.prNumber());
        assertTrue(response.mergeable());
        assertFalse(response.hasConflicts());
        assertNull(response.reason());
    }

    @Test
    void pullRequestMergeabilityResponseConRazon() {
        PullRequestMergeabilityResponse response = new PullRequestMergeabilityResponse(
                42, false, true, "Has conflicts");

        assertEquals(42, response.prNumber());
        assertFalse(response.mergeable());
        assertTrue(response.hasConflicts());
        assertEquals("Has conflicts", response.reason());
    }
}
