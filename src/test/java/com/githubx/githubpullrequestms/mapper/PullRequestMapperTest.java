package com.githubx.githubpullrequestms.mapper;

import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PullRequestMapperTest {

    private PullRequestMapper mapper;
    private RepositoryEntity repository;
    private UUID authorId;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PullRequestMapper.class);
        authorId = UUID.randomUUID();

        repository = RepositoryEntity.builder()
                .id(1L)
                .owner("owner")
                .name("repo")
                .defaultBranch("main")
                .build();
    }

    @Test
    void debeMapearPullRequestEntityAResponse() {
        Instant now = Instant.now();
        PullRequestEntity entity = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(1)
                .title("Test PR")
                .description("Description")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .hasConflicts(false)
                .commitsCount(5)
                .createdAt(now)
                .updatedAt(now)
                .mergedAt(null)
                .build();

        PullRequestResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(entity.getId(), result.id());
        assertEquals("1", result.repoId());
        assertEquals(1, result.number());
        assertEquals("Test PR", result.title());
        assertEquals("Description", result.description());
        assertEquals("feature", result.sourceBranch());
        assertEquals("main", result.targetBranch());
        assertNotNull(result.author());
        assertEquals(authorId, result.author().id());
        assertEquals("testuser", result.author().username());
        assertEquals(PrStatus.OPEN, result.status());
        assertFalse(result.hasConflicts());
        assertEquals(5, result.commitsCount());
        assertNotNull(result.createdAt());
        assertNotNull(result.updatedAt());
        assertNull(result.mergedAt());
    }

    @Test
    void debeMapearPullRequestEntityConMergedAt() {
        Instant now = Instant.now();
        PullRequestEntity entity = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(1)
                .title("Merged PR")
                .description("Description")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.MERGED)
                .hasConflicts(false)
                .commitsCount(3)
                .createdAt(now)
                .updatedAt(now)
                .mergedAt(now)
                .build();

        PullRequestResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(PrStatus.MERGED, result.status());
        assertNotNull(result.mergedAt());
    }

    @Test
    void debeMapearListaDePullRequestEntities() {
        Instant now = Instant.now();
        PullRequestEntity entity1 = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(1)
                .title("PR 1")
                .description("Description 1")
                .sourceBranch("feature1")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .hasConflicts(false)
                .commitsCount(1)
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestEntity entity2 = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(2)
                .title("PR 2")
                .description("Description 2")
                .sourceBranch("feature2")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.CLOSED)
                .hasConflicts(true)
                .commitsCount(2)
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<PullRequestResponse> result = mapper.toResponseList(List.of(entity1, entity2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("PR 1", result.get(0).title());
        assertEquals("PR 2", result.get(1).title());
        assertFalse(result.get(0).hasConflicts());
        assertTrue(result.get(1).hasConflicts());
    }

    @Test
    void debeMapearListaVacia() {
        List<PullRequestResponse> result = mapper.toResponseList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void debeFormatearInstantCorrectamente() {
        Instant instant = Instant.parse("2026-01-15T10:30:00Z");
        String result = mapper.formatInstant(instant);
        assertEquals("2026-01-15T10:30:00Z", result);
    }

    @Test
    void debeRetornarNullParaInstantNull() {
        assertNull(mapper.formatInstant(null));
    }

    @Test
    void debeCrearAuthorSummaryCorrectamente() {
        Instant now = Instant.now();
        PullRequestEntity entity = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(1)
                .title("Test")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("john_doe")
                .status(PrStatus.OPEN)
                .hasConflicts(false)
                .commitsCount(1)
                .createdAt(now)
                .updatedAt(now)
                .build();

        AuthorSummaryResponse result = mapper.toAuthorSummary(entity);

        assertNotNull(result);
        assertEquals(authorId, result.id());
        assertEquals("john_doe", result.username());
    }

    @Test
    void debeMapearPullRequestConConflictos() {
        Instant now = Instant.now();
        PullRequestEntity entity = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(1)
                .title("Conflicting PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .hasConflicts(true)
                .commitsCount(1)
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertTrue(result.hasConflicts());
    }

    @Test
    void debeMapearPullRequestConDescripcionNull() {
        Instant now = Instant.now();
        PullRequestEntity entity = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(1)
                .title("No description PR")
                .description(null)
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .hasConflicts(false)
                .commitsCount(1)
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertNull(result.description());
    }
}
