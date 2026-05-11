package com.githubx.githubpullrequestms.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumsTest {

    @Test
    void prStatusDebeContenerValoresCorrectos() {
        assertEquals(3, PrStatus.values().length);
        assertNotNull(PrStatus.OPEN);
        assertNotNull(PrStatus.CLOSED);
        assertNotNull(PrStatus.MERGED);
    }

    @Test
    void prStatusValueOf() {
        assertEquals(PrStatus.OPEN, PrStatus.valueOf("OPEN"));
        assertEquals(PrStatus.CLOSED, PrStatus.valueOf("CLOSED"));
        assertEquals(PrStatus.MERGED, PrStatus.valueOf("MERGED"));
    }

    @Test
    void repoVisibilityDebeContenerValoresCorrectos() {
        assertEquals(2, RepoVisibility.values().length);
        assertNotNull(RepoVisibility.PUBLIC);
        assertNotNull(RepoVisibility.PRIVATE);
    }

    @Test
    void repoVisibilityValueOf() {
        assertEquals(RepoVisibility.PUBLIC, RepoVisibility.valueOf("PUBLIC"));
        assertEquals(RepoVisibility.PRIVATE, RepoVisibility.valueOf("PRIVATE"));
    }

    @Test
    void repoVisibilityFromValue() {
        assertEquals(RepoVisibility.PUBLIC, RepoVisibility.fromValue("public"));
        assertEquals(RepoVisibility.PRIVATE, RepoVisibility.fromValue("private"));
    }

    @Test
    void repoVisibilityGetValue() {
        assertEquals("public", RepoVisibility.PUBLIC.getValue());
        assertEquals("private", RepoVisibility.PRIVATE.getValue());
    }

    @Test
    void reviewDecisionDebeContenerValoresCorrectos() {
        assertEquals(3, ReviewDecision.values().length);
        assertNotNull(ReviewDecision.APPROVED);
        assertNotNull(ReviewDecision.CHANGES_REQUESTED);
        assertNotNull(ReviewDecision.COMMENTED);
    }

    @Test
    void reviewDecisionValueOf() {
        assertEquals(ReviewDecision.APPROVED, ReviewDecision.valueOf("APPROVED"));
        assertEquals(ReviewDecision.CHANGES_REQUESTED, ReviewDecision.valueOf("CHANGES_REQUESTED"));
        assertEquals(ReviewDecision.COMMENTED, ReviewDecision.valueOf("COMMENTED"));
    }

    @Test
    void mergeStrategyDebeContenerValoresCorrectos() {
        assertEquals(3, MergeStrategy.values().length);
        assertNotNull(MergeStrategy.MERGE);
        assertNotNull(MergeStrategy.SQUASH);
        assertNotNull(MergeStrategy.REBASE);
    }

    @Test
    void mergeStrategyValueOf() {
        assertEquals(MergeStrategy.MERGE, MergeStrategy.valueOf("MERGE"));
        assertEquals(MergeStrategy.SQUASH, MergeStrategy.valueOf("SQUASH"));
        assertEquals(MergeStrategy.REBASE, MergeStrategy.valueOf("REBASE"));
    }

    @Test
    void prStatusGetValue() {
        assertEquals("open", PrStatus.OPEN.getValue());
        assertEquals("closed", PrStatus.CLOSED.getValue());
        assertEquals("merged", PrStatus.MERGED.getValue());
    }

    @Test
    void prStatusFromValue() {
        assertEquals(PrStatus.OPEN, PrStatus.fromValue("open"));
        assertEquals(PrStatus.CLOSED, PrStatus.fromValue("closed"));
        assertEquals(PrStatus.MERGED, PrStatus.fromValue("merged"));
    }

    @Test
    void prStatusFromValueCaseInsensitive() {
        assertEquals(PrStatus.OPEN, PrStatus.fromValue("OPEN"));
        assertEquals(PrStatus.CLOSED, PrStatus.fromValue("Closed"));
    }

    @Test
    void prStatusFromValueInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> PrStatus.fromValue("invalid"));
    }

    @Test
    void mergeStrategyGetValue() {
        assertEquals("merge", MergeStrategy.MERGE.getValue());
        assertEquals("squash", MergeStrategy.SQUASH.getValue());
        assertEquals("rebase", MergeStrategy.REBASE.getValue());
    }

    @Test
    void mergeStrategyFromValue() {
        assertEquals(MergeStrategy.MERGE, MergeStrategy.fromValue("merge"));
        assertEquals(MergeStrategy.SQUASH, MergeStrategy.fromValue("squash"));
        assertEquals(MergeStrategy.REBASE, MergeStrategy.fromValue("rebase"));
    }

    @Test
    void mergeStrategyFromValueCaseInsensitive() {
        assertEquals(MergeStrategy.MERGE, MergeStrategy.fromValue("MERGE"));
        assertEquals(MergeStrategy.SQUASH, MergeStrategy.fromValue("Squash"));
    }

    @Test
    void mergeStrategyFromValueInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> MergeStrategy.fromValue("invalid"));
    }

    @Test
    void reviewDecisionGetValue() {
        assertEquals("approved", ReviewDecision.APPROVED.getValue());
        assertEquals("changes_requested", ReviewDecision.CHANGES_REQUESTED.getValue());
        assertEquals("commented", ReviewDecision.COMMENTED.getValue());
    }

    @Test
    void reviewDecisionFromValue() {
        assertEquals(ReviewDecision.APPROVED, ReviewDecision.fromValue("approved"));
        assertEquals(ReviewDecision.CHANGES_REQUESTED, ReviewDecision.fromValue("changes_requested"));
        assertEquals(ReviewDecision.COMMENTED, ReviewDecision.fromValue("commented"));
    }

    @Test
    void reviewDecisionFromValueCaseInsensitive() {
        assertEquals(ReviewDecision.APPROVED, ReviewDecision.fromValue("APPROVED"));
        assertEquals(ReviewDecision.CHANGES_REQUESTED, ReviewDecision.fromValue("Changes_Requested"));
    }

    @Test
    void reviewDecisionFromValueInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> ReviewDecision.fromValue("invalid"));
    }

    @Test
    void repoVisibilityFromValueInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> RepoVisibility.fromValue("invalid"));
    }

    @Test
    void repoVisibilityFromValueCaseInsensitive() {
        assertEquals(RepoVisibility.PUBLIC, RepoVisibility.fromValue("PUBLIC"));
        assertEquals(RepoVisibility.PRIVATE, RepoVisibility.fromValue("Private"));
    }
}
