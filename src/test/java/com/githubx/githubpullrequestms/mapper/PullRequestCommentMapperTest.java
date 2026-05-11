package com.githubx.githubpullrequestms.mapper;

import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.model.PullRequestCommentEntity;
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

class PullRequestCommentMapperTest {

    private PullRequestCommentMapper mapper;
    private PullRequestEntity pullRequest;
    private UUID authorId;
    private UUID pullRequestId;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(PullRequestCommentMapper.class);
        authorId = UUID.randomUUID();
        pullRequestId = UUID.randomUUID();

        RepositoryEntity repository = RepositoryEntity.builder()
                .id(1L)
                .owner("owner")
                .name("repo")
                .defaultBranch("main")
                .build();

        pullRequest = PullRequestEntity.builder()
                .id(pullRequestId)
                .repository(repository)
                .number(1)
                .title("Test PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(authorId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .hasConflicts(false)
                .commitsCount(1)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void debeMapearPullRequestCommentEntityAResponse() {
        Instant now = Instant.now();
        PullRequestCommentEntity entity = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("Great code!")
                .filePath("src/main/java/Main.java")
                .lineNumber(10)
                .authorId(authorId)
                .authorUsername("reviewer")
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestCommentResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(entity.getId(), result.id());
        assertEquals(pullRequestId, result.pullRequestId());
        assertEquals("Great code!", result.body());
        assertEquals("src/main/java/Main.java", result.filePath());
        assertEquals(10, result.lineNumber());
        assertNotNull(result.author());
        assertEquals(authorId, result.author().id());
        assertEquals("reviewer", result.author().username());
        assertNotNull(result.createdAt());
        assertNotNull(result.updatedAt());
    }

    @Test
    void debeMapearCommentSinFilePath() {
        Instant now = Instant.now();
        PullRequestCommentEntity entity = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("General comment")
                .filePath(null)
                .lineNumber(null)
                .authorId(authorId)
                .authorUsername("reviewer")
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestCommentResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals("General comment", result.body());
        assertNull(result.filePath());
        assertNull(result.lineNumber());
    }

    @Test
    void debeMapearListaDeComments() {
        Instant now = Instant.now();
        PullRequestCommentEntity entity1 = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("Comment 1")
                .filePath("file1.java")
                .lineNumber(5)
                .authorId(authorId)
                .authorUsername("user1")
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestCommentEntity entity2 = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("Comment 2")
                .filePath("file2.java")
                .lineNumber(15)
                .authorId(UUID.randomUUID())
                .authorUsername("user2")
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<PullRequestCommentResponse> result = mapper.toResponseList(List.of(entity1, entity2));

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Comment 1", result.get(0).body());
        assertEquals("Comment 2", result.get(1).body());
        assertEquals("user1", result.get(0).author().username());
        assertEquals("user2", result.get(1).author().username());
    }

    @Test
    void debeMapearListaVacia() {
        List<PullRequestCommentResponse> result = mapper.toResponseList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void debeCrearAuthorSummaryCorrectamente() {
        Instant now = Instant.now();
        PullRequestCommentEntity entity = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("Comment")
                .authorId(authorId)
                .authorUsername("john_doe")
                .createdAt(now)
                .updatedAt(now)
                .build();

        AuthorSummaryResponse result = mapper.toAuthorSummary(entity);

        assertNotNull(result);
        assertEquals(authorId, result.id());
        assertEquals("john_doe", result.username());
    }

    @Test
    void debeFormatearInstantCorrectamente() {
        Instant instant = Instant.parse("2026-03-20T14:45:30Z");
        String result = mapper.formatInstant(instant);
        assertEquals("2026-03-20T14:45:30Z", result);
    }

    @Test
    void debeRetornarNullParaInstantNull() {
        assertNull(mapper.formatInstant(null));
    }

    @Test
    void debeMapearCommentConLineNumberSinFilePath() {
        Instant now = Instant.now();
        PullRequestCommentEntity entity = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("Line comment without file")
                .filePath(null)
                .lineNumber(25)
                .authorId(authorId)
                .authorUsername("reviewer")
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestCommentResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertNull(result.filePath());
        assertEquals(25, result.lineNumber());
    }

    @Test
    void debeMapearCommentConBodyLargo() {
        Instant now = Instant.now();
        String longBody = "This is a very long comment. ".repeat(100);
        PullRequestCommentEntity entity = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body(longBody)
                .authorId(authorId)
                .authorUsername("reviewer")
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestCommentResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(longBody, result.body());
    }

    @Test
    void debeMapearCommentConFilePathLargo() {
        Instant now = Instant.now();
        String longFilePath = "src/main/java/com/example/very/deep/nested/package/structure/MyVeryLongClassName.java";
        PullRequestCommentEntity entity = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("Comment on deeply nested file")
                .filePath(longFilePath)
                .lineNumber(100)
                .authorId(authorId)
                .authorUsername("reviewer")
                .createdAt(now)
                .updatedAt(now)
                .build();

        PullRequestCommentResponse result = mapper.toResponse(entity);

        assertNotNull(result);
        assertEquals(longFilePath, result.filePath());
        assertEquals(100, result.lineNumber());
    }
}
