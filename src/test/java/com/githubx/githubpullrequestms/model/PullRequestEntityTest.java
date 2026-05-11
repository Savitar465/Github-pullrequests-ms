package com.githubx.githubpullrequestms.model;

import com.githubx.githubpullrequestms.model.enums.PrStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PullRequestEntityTest {

    @Test
    void debeCrearPullRequestConBuilder() {
        UUID id = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("owner")
                .name("repo")
                .build();

        PullRequestEntity pr = PullRequestEntity.builder()
                .id(id)
                .repository(repo)
                .number(1)
                .title("Test PR")
                .description("Description")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .build();

        assertEquals(id, pr.getId());
        assertEquals(1, pr.getNumber());
        assertEquals("Test PR", pr.getTitle());
        assertEquals("Description", pr.getDescription());
        assertEquals("feature", pr.getSourceBranch());
        assertEquals("main", pr.getTargetBranch());
        assertEquals(authorId, pr.getAuthorId());
        assertEquals("testuser", pr.getAuthorUsername());
        assertEquals(PrStatus.OPEN, pr.getStatus());
    }

    @Test
    void debeUsarValoresPorDefecto() {
        PullRequestEntity pr = PullRequestEntity.builder()
                .number(1)
                .title("Test")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(UUID.randomUUID())
                .authorUsername("user")
                .build();

        assertEquals(PrStatus.OPEN, pr.getStatus());
        assertFalse(pr.getHasConflicts());
        assertEquals(0, pr.getCommitsCount());
        assertNotNull(pr.getReviews());
        assertNotNull(pr.getComments());
        assertTrue(pr.getReviews().isEmpty());
        assertTrue(pr.getComments().isEmpty());
    }

    @Test
    void debeGenerarHtmlUrl() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("owner")
                .name("repo")
                .build();

        PullRequestEntity pr = PullRequestEntity.builder()
                .repository(repo)
                .number(42)
                .title("Test")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(UUID.randomUUID())
                .authorUsername("user")
                .build();

        assertEquals("/v1/repos/owner/repo/pulls/42", pr.getHtmlUrl());
    }

    @Test
    void debeSetearYGetearPropiedades() {
        PullRequestEntity pr = new PullRequestEntity();
        UUID id = UUID.randomUUID();
        UUID mergedById = UUID.randomUUID();
        Instant now = Instant.now();

        pr.setId(id);
        pr.setNumber(5);
        pr.setTitle("Updated Title");
        pr.setDescription("Updated Description");
        pr.setSourceBranch("dev");
        pr.setTargetBranch("main");
        pr.setStatus(PrStatus.MERGED);
        pr.setHasConflicts(true);
        pr.setCommitsCount(10);
        pr.setMergedAt(now);
        pr.setMergedById(mergedById);
        pr.setMergedByUsername("merger");

        assertEquals(id, pr.getId());
        assertEquals(5, pr.getNumber());
        assertEquals("Updated Title", pr.getTitle());
        assertEquals("Updated Description", pr.getDescription());
        assertEquals("dev", pr.getSourceBranch());
        assertEquals("main", pr.getTargetBranch());
        assertEquals(PrStatus.MERGED, pr.getStatus());
        assertTrue(pr.getHasConflicts());
        assertEquals(10, pr.getCommitsCount());
        assertEquals(now, pr.getMergedAt());
        assertEquals(mergedById, pr.getMergedById());
        assertEquals("merger", pr.getMergedByUsername());
    }

    @Test
    void debeCrearPullRequestCerrado() {
        PullRequestEntity pr = PullRequestEntity.builder()
                .number(1)
                .title("Closed PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(UUID.randomUUID())
                .authorUsername("user")
                .status(PrStatus.CLOSED)
                .build();

        assertEquals(PrStatus.CLOSED, pr.getStatus());
    }

    @Test
    void debeCrearPullRequestMergeado() {
        Instant mergedAt = Instant.now();
        UUID mergedById = UUID.randomUUID();

        PullRequestEntity pr = PullRequestEntity.builder()
                .number(1)
                .title("Merged PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(UUID.randomUUID())
                .authorUsername("user")
                .status(PrStatus.MERGED)
                .mergedAt(mergedAt)
                .mergedById(mergedById)
                .mergedByUsername("admin")
                .build();

        assertEquals(PrStatus.MERGED, pr.getStatus());
        assertEquals(mergedAt, pr.getMergedAt());
        assertEquals(mergedById, pr.getMergedById());
        assertEquals("admin", pr.getMergedByUsername());
    }
}
