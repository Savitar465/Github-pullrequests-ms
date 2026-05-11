package com.githubx.githubpullrequestms.service.implementacion;

import com.githubx.githubpullrequestms.dao.PullRequestCommentDao;
import com.githubx.githubpullrequestms.dao.PullRequestDao;
import com.githubx.githubpullrequestms.dao.RepositoryDao;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestCommentsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.mapper.PullRequestCommentMapper;
import com.githubx.githubpullrequestms.model.PullRequestCommentEntity;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PullRequestCommentServiceTest {

    @Mock
    private PullRequestCommentDao commentDao;

    @Mock
    private PullRequestDao pullRequestDao;

    @Mock
    private RepositoryDao repositoryDao;

    @Mock
    private PullRequestCommentMapper commentMapper;

    @InjectMocks
    private PullRequestCommentServiceImpl commentService;

    private RepositoryEntity repository;
    private PullRequestEntity pullRequest;
    private PullRequestCommentEntity comment;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        repository = RepositoryEntity.builder()
                .id(1L)
                .owner("owner")
                .name("repo")
                .defaultBranch("main")
                .build();

        pullRequest = PullRequestEntity.builder()
                .id(UUID.randomUUID())
                .repository(repository)
                .number(1)
                .title("Test PR")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(userId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .build();

        comment = PullRequestCommentEntity.builder()
                .id(UUID.randomUUID())
                .pullRequest(pullRequest)
                .body("Test comment")
                .authorId(userId)
                .authorUsername("testuser")
                .build();
    }

    @Test
    void debeListarComentarios() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(commentDao.findByPullRequestOrderByCreatedAtAsc(pullRequest))
                .thenReturn(List.of(comment));
        when(commentMapper.toResponseList(any()))
                .thenReturn(List.of(new PullRequestCommentResponse(
                        comment.getId(), pullRequest.getId(), "Test comment",
                        null, null,
                        new AuthorSummaryResponse(userId, "testuser"),
                        "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z")));

        ListPullRequestCommentsResponse result = commentService.listComments("owner", "repo", 1);

        assertNotNull(result);
        assertEquals(1, result.comments().size());
        verify(commentDao).findByPullRequestOrderByCreatedAtAsc(pullRequest);
    }

    @Test
    void debeListarComentariosVacios() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(commentDao.findByPullRequestOrderByCreatedAtAsc(pullRequest))
                .thenReturn(Collections.emptyList());
        when(commentMapper.toResponseList(any()))
                .thenReturn(Collections.emptyList());

        ListPullRequestCommentsResponse result = commentService.listComments("owner", "repo", 1);

        assertNotNull(result);
        assertTrue(result.comments().isEmpty());
    }

    @Test
    void debeCrearComentario() {
        CreatePullRequestCommentRequest request = new CreatePullRequestCommentRequest(
                "New comment", null, null);

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(commentDao.save(any(PullRequestCommentEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(commentMapper.toResponse(any()))
                .thenReturn(new PullRequestCommentResponse(
                        UUID.randomUUID(), pullRequest.getId(), "New comment",
                        null, null,
                        new AuthorSummaryResponse(userId, "testuser"),
                        "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z"));

        PullRequestCommentResponse result = commentService.createComment(
                "owner", "repo", 1, request, userId.toString(), "testuser");

        assertNotNull(result);
        assertEquals("New comment", result.body());
        verify(commentDao).save(any(PullRequestCommentEntity.class));
    }

    @Test
    void debeCrearComentarioConFilePath() {
        CreatePullRequestCommentRequest request = new CreatePullRequestCommentRequest(
                "Line comment", "src/main/java/Test.java", 42);

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(commentDao.save(any(PullRequestCommentEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(commentMapper.toResponse(any()))
                .thenReturn(new PullRequestCommentResponse(
                        UUID.randomUUID(), pullRequest.getId(), "Line comment",
                        "src/main/java/Test.java", 42,
                        new AuthorSummaryResponse(userId, "testuser"),
                        "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z"));

        PullRequestCommentResponse result = commentService.createComment(
                "owner", "repo", 1, request, userId.toString(), "testuser");

        assertNotNull(result);
        assertEquals("src/main/java/Test.java", result.filePath());
        assertEquals(42, result.lineNumber());
    }

    @Test
    void debeLanzarExcepcionSiRepositorioNoExiste() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                commentService.listComments("owner", "repo", 1));
    }

    @Test
    void debeLanzarExcepcionSiPullRequestNoExiste() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                commentService.listComments("owner", "repo", 1));
    }

    @Test
    void debeLanzarExcepcionAlCrearComentarioSiPRNoExiste() {
        CreatePullRequestCommentRequest request = new CreatePullRequestCommentRequest(
                "Comment", null, null);

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                commentService.createComment("owner", "repo", 1, request, userId.toString(), "testuser"));
    }
}
