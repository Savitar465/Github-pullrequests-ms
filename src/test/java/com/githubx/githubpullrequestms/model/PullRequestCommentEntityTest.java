package com.githubx.githubpullrequestms.model;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PullRequestCommentEntityTest {

    @Test
    void debeCrearComentarioConBuilder() {
        UUID id = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        PullRequestEntity pr = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .number(1)
                .title("Test PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(UUID.randomUUID())
                .authorUsername("user")
                .build();

        PullRequestCommentEntity comment = PullRequestCommentEntity.builder()
                .id(id)
                .pullRequest(pr)
                .body("This is a comment")
                .authorId(authorId)
                .authorUsername("commenter")
                .build();

        assertEquals(id, comment.getId());
        assertEquals(pr, comment.getPullRequest());
        assertEquals("This is a comment", comment.getBody());
        assertEquals(authorId, comment.getAuthorId());
        assertEquals("commenter", comment.getAuthorUsername());
    }

    @Test
    void debeCrearComentarioEnLinea() {
        UUID authorId = UUID.randomUUID();

        PullRequestCommentEntity comment = PullRequestCommentEntity.builder()
                .body("Line comment")
                .filePath("src/main/java/Test.java")
                .lineNumber(42)
                .authorId(authorId)
                .authorUsername("reviewer")
                .build();

        assertEquals("Line comment", comment.getBody());
        assertEquals("src/main/java/Test.java", comment.getFilePath());
        assertEquals(42, comment.getLineNumber());
    }

    @Test
    void debeSetearYGetearPropiedades() {
        PullRequestCommentEntity comment = new PullRequestCommentEntity();
        UUID id = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();
        Instant now = Instant.now();

        comment.setId(id);
        comment.setBody("Updated comment");
        comment.setFilePath("path/to/file.java");
        comment.setLineNumber(100);
        comment.setAuthorId(authorId);
        comment.setAuthorUsername("updater");
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        assertEquals(id, comment.getId());
        assertEquals("Updated comment", comment.getBody());
        assertEquals("path/to/file.java", comment.getFilePath());
        assertEquals(100, comment.getLineNumber());
        assertEquals(authorId, comment.getAuthorId());
        assertEquals("updater", comment.getAuthorUsername());
        assertEquals(now, comment.getCreatedAt());
        assertEquals(now, comment.getUpdatedAt());
    }

    @Test
    void debeCrearComentarioSinFilePath() {
        PullRequestCommentEntity comment = PullRequestCommentEntity.builder()
                .body("General comment")
                .authorId(UUID.randomUUID())
                .authorUsername("user")
                .build();

        assertEquals("General comment", comment.getBody());
        assertNull(comment.getFilePath());
        assertNull(comment.getLineNumber());
    }
}
