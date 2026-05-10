package com.githubx.githubpullrequestms.service.implementacion;

import com.githubx.githubpullrequestms.dao.PullRequestDao;
import com.githubx.githubpullrequestms.dao.PullRequestReviewDao;
import com.githubx.githubpullrequestms.dao.RepositoryDao;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.mapper.PullRequestMapper;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import com.githubx.githubpullrequestms.util.errorhandling.BadRequestException;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PullRequestServiceTest {

    @Mock
    private PullRequestDao pullRequestDao;

    @Mock
    private RepositoryDao repositoryDao;

    @Mock
    private PullRequestReviewDao reviewDao;

    @Mock
    private PullRequestMapper pullRequestMapper;

    @InjectMocks
    private PullRequestServiceImpl pullRequestService;

    private RepositoryEntity repository;
    private PullRequestEntity pullRequest;
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
                .description("Description")
                .sourceBranch("feature")
                .targetBranch("main")
                .authorId(userId)
                .authorUsername("testuser")
                .status(PrStatus.OPEN)
                .hasConflicts(false)
                .commitsCount(1)
                .build();
    }

    @Test
    void debeListarPullRequestsPorRepositorio() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryOrderByCreatedAtDesc(repository))
                .thenReturn(Collections.emptyList());
        when(pullRequestMapper.toResponseList(anyList()))
                .thenReturn(Collections.emptyList());

        ListPullRequestsResponse result = pullRequestService.listPullRequests("owner", "repo", null);

        assertNotNull(result);
        assertTrue(result.pullRequests().isEmpty());
        verify(pullRequestDao).findByRepositoryOrderByCreatedAtDesc(repository);
    }

    @Test
    void debeListarPullRequestsFiltradosPorStatus() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndStatusOrderByCreatedAtDesc(repository, PrStatus.OPEN))
                .thenReturn(Collections.emptyList());
        when(pullRequestMapper.toResponseList(anyList()))
                .thenReturn(Collections.emptyList());

        ListPullRequestsResponse result = pullRequestService.listPullRequests("owner", "repo", PrStatus.OPEN);

        assertNotNull(result);
        verify(pullRequestDao).findByRepositoryAndStatusOrderByCreatedAtDesc(repository, PrStatus.OPEN);
    }

    @Test
    void debeCrearPullRequest() {
        CreatePullRequestRequest request = new CreatePullRequestRequest(
                "New PR", "Description", "feature", "main");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.getNextPrNumber(repository)).thenReturn(1);
        when(pullRequestDao.save(any(PullRequestEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(pullRequestMapper.toResponse(any()))
                .thenReturn(new PullRequestResponse(
                        UUID.randomUUID(), "1", 1, "New PR", "Description",
                        "feature", "main",
                        new AuthorSummaryResponse(userId, "testuser"),
                        PrStatus.OPEN, false, 0,
                        "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null));

        PullRequestResponse result = pullRequestService.createPullRequest(
                "owner", "repo", request, userId.toString(), "testuser");

        assertNotNull(result);
        assertEquals("New PR", result.title());
        assertEquals(PrStatus.OPEN, result.status());
        verify(pullRequestDao).save(any(PullRequestEntity.class));
    }

    @Test
    void debeLanzarExcepcionSiRamasIguales() {
        CreatePullRequestRequest request = new CreatePullRequestRequest(
                "New PR", "Description", "main", "main");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));

        assertThrows(BadRequestException.class, () ->
                pullRequestService.createPullRequest("owner", "repo", request, userId.toString(), "testuser"));
    }

    @Test
    void debeLanzarExcepcionSiRepositorioNoExiste() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                pullRequestService.listPullRequests("owner", "repo", null));
    }

    @Test
    void debeObtenerPullRequest() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestMapper.toResponse(pullRequest))
                .thenReturn(new PullRequestResponse(
                        pullRequest.getId(), "1", 1, "Test PR", "Description",
                        "feature", "main",
                        new AuthorSummaryResponse(userId, "testuser"),
                        PrStatus.OPEN, false, 1,
                        "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null));

        PullRequestResponse result = pullRequestService.getPullRequest("owner", "repo", 1);

        assertNotNull(result);
        assertEquals("Test PR", result.title());
    }

    @Test
    void debeVerificarMergeabilidad() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(true);
        when(pullRequestDao.countChangesRequestedReviews(pullRequest)).thenReturn(0);

        PullRequestMergeabilityResponse result =
                pullRequestService.getPullRequestMergeability("owner", "repo", 1);

        assertNotNull(result);
        assertTrue(result.mergeable());
        assertFalse(result.hasConflicts());
    }

    @Test
    void debeIndicarNoMergeableSiTieneConflictos() {
        pullRequest.setHasConflicts(true);

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(true);
        when(pullRequestDao.countChangesRequestedReviews(pullRequest)).thenReturn(0);

        PullRequestMergeabilityResponse result =
                pullRequestService.getPullRequestMergeability("owner", "repo", 1);

        assertNotNull(result);
        assertFalse(result.mergeable());
        assertTrue(result.hasConflicts());
        assertEquals("El pull request tiene conflictos", result.reason());
    }
}
