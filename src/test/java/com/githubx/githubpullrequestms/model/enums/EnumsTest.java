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
}
