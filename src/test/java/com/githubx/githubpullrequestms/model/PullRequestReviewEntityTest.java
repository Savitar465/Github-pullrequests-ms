package com.githubx.githubpullrequestms.model;

import com.githubx.githubpullrequestms.model.enums.ReviewDecision;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PullRequestReviewEntityTest {

    @Test
    void debeCrearReviewConBuilder() {
        UUID id = UUID.randomUUID();
        UUID reviewerId = UUID.randomUUID();
        PullRequestEntity pr = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .number(1)
                .title("Test PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(UUID.randomUUID())
                .authorUsername("author")
                .build();

        PullRequestReviewEntity review = PullRequestReviewEntity.builder()
                .id(id)
                .pullRequest(pr)
                .reviewerId(reviewerId)
                .reviewerUsername("reviewer")
                .decision(ReviewDecision.APPROVED)
                .comment("LGTM!")
                .build();

        assertEquals(id, review.getId());
        assertEquals(pr, review.getPullRequest());
        assertEquals(reviewerId, review.getReviewerId());
        assertEquals("reviewer", review.getReviewerUsername());
        assertEquals(ReviewDecision.APPROVED, review.getDecision());
        assertEquals("LGTM!", review.getComment());
    }

    @Test
    void debeCrearReviewConChangesRequested() {
        UUID reviewerId = UUID.randomUUID();

        PullRequestReviewEntity review = PullRequestReviewEntity.builder()
                .reviewerId(reviewerId)
                .reviewerUsername("reviewer")
                .decision(ReviewDecision.CHANGES_REQUESTED)
                .comment("Please fix the bug")
                .build();

        assertEquals(ReviewDecision.CHANGES_REQUESTED, review.getDecision());
        assertEquals("Please fix the bug", review.getComment());
    }

    @Test
    void debeCrearReviewConCommented() {
        UUID reviewerId = UUID.randomUUID();

        PullRequestReviewEntity review = PullRequestReviewEntity.builder()
                .reviewerId(reviewerId)
                .reviewerUsername("reviewer")
                .decision(ReviewDecision.COMMENTED)
                .comment("Just a comment")
                .build();

        assertEquals(ReviewDecision.COMMENTED, review.getDecision());
    }

    @Test
    void debeSetearYGetearPropiedades() {
        PullRequestReviewEntity review = new PullRequestReviewEntity();
        UUID id = UUID.randomUUID();
        UUID reviewerId = UUID.randomUUID();
        Instant now = Instant.now();

        review.setId(id);
        review.setReviewerId(reviewerId);
        review.setReviewerUsername("new_reviewer");
        review.setDecision(ReviewDecision.APPROVED);
        review.setComment("Updated comment");
        review.setCreatedAt(now);
        review.setUpdatedAt(now);

        assertEquals(id, review.getId());
        assertEquals(reviewerId, review.getReviewerId());
        assertEquals("new_reviewer", review.getReviewerUsername());
        assertEquals(ReviewDecision.APPROVED, review.getDecision());
        assertEquals("Updated comment", review.getComment());
        assertEquals(now, review.getCreatedAt());
        assertEquals(now, review.getUpdatedAt());
    }

    @Test
    void debeCrearReviewSinComentario() {
        PullRequestReviewEntity review = PullRequestReviewEntity.builder()
                .reviewerId(UUID.randomUUID())
                .reviewerUsername("reviewer")
                .decision(ReviewDecision.APPROVED)
                .build();

        assertEquals(ReviewDecision.APPROVED, review.getDecision());
        assertNull(review.getComment());
    }

    @Test
    void debePoseerPullRequest() {
        PullRequestEntity pr = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .number(42)
                .title("Test PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(UUID.randomUUID())
                .authorUsername("author")
                .build();

        PullRequestReviewEntity review = PullRequestReviewEntity.builder()
                .pullRequest(pr)
                .reviewerId(UUID.randomUUID())
                .reviewerUsername("reviewer")
                .decision(ReviewDecision.APPROVED)
                .build();

        assertNotNull(review.getPullRequest());
        assertEquals(42, review.getPullRequest().getNumber());
    }
}
