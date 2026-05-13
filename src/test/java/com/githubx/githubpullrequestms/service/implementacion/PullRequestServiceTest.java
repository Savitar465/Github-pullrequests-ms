package com.githubx.githubpullrequestms.service.implementacion;

import com.githubx.githubpullrequestms.dao.PullRequestDao;
import com.githubx.githubpullrequestms.dao.PullRequestReviewDao;
import com.githubx.githubpullrequestms.dao.RepositoryDao;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.mapper.PullRequestMapper;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.PullRequestReviewEntity;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.model.enums.MergeStrategy;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import com.githubx.githubpullrequestms.model.enums.ReviewDecision;
import com.githubx.githubpullrequestms.util.errorhandling.BadRequestException;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import com.githubx.githubpullrequestms.util.errorhandling.UnprocessableEntityException;
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

    @Test
    void debeRevisarPullRequest() {
        ReviewPullRequestRequest request = new ReviewPullRequestRequest(
                ReviewDecision.APPROVED, "LGTM!");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(reviewDao.save(any(PullRequestReviewEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(pullRequestMapper.toResponse(any()))
                .thenReturn(new PullRequestResponse(
                        pullRequest.getId(), "1", 1, "Test PR", "Description",
                        "feature", "main",
                        new AuthorSummaryResponse(userId, "testuser"),
                        PrStatus.OPEN, false, 1,
                        "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null));

        PullRequestResponse result = pullRequestService.reviewPullRequest(
                "owner", "repo", 1, request, userId.toString(), "reviewer");

        assertNotNull(result);
        verify(reviewDao).save(any(PullRequestReviewEntity.class));
    }

    @Test
    void debeLanzarExcepcionAlRevisarPullRequestCerrado() {
        pullRequest.setStatus(PrStatus.CLOSED);
        ReviewPullRequestRequest request = new ReviewPullRequestRequest(
                ReviewDecision.APPROVED, "LGTM!");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));

        assertThrows(BadRequestException.class, () ->
                pullRequestService.reviewPullRequest("owner", "repo", 1, request, userId.toString(), "reviewer"));
    }

    @Test
    void debeMergearPullRequest() {
        MergePullRequestRequest request = new MergePullRequestRequest(
                MergeStrategy.MERGE, "Merge PR #1");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(true);
        when(pullRequestDao.countChangesRequestedReviews(pullRequest)).thenReturn(0);
        when(pullRequestDao.save(any(PullRequestEntity.class)))
                .thenAnswer(inv -> inv.getArgument(0));
        when(pullRequestMapper.toResponse(any()))
                .thenReturn(new PullRequestResponse(
                        pullRequest.getId(), "1", 1, "Test PR", "Description",
                        "feature", "main",
                        new AuthorSummaryResponse(userId, "testuser"),
                        PrStatus.MERGED, false, 1,
                        "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null));

        PullRequestResponse result = pullRequestService.mergePullRequest(
                "owner", "repo", 1, request, userId.toString(), "merger", null);

        assertNotNull(result);
        assertEquals(PrStatus.MERGED, result.status());
        verify(pullRequestDao).save(any(PullRequestEntity.class));
    }

    @Test
    void debeLanzarExcepcionAlMergearPullRequestCerrado() {
        pullRequest.setStatus(PrStatus.CLOSED);
        MergePullRequestRequest request = new MergePullRequestRequest(
                MergeStrategy.MERGE, "Merge PR #1");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));

        assertThrows(BadRequestException.class, () ->
                pullRequestService.mergePullRequest("owner", "repo", 1, request, userId.toString(), "merger", null));
    }

    @Test
    void debeLanzarExcepcionAlMergearPullRequestConConflictos() {
        pullRequest.setHasConflicts(true);
        MergePullRequestRequest request = new MergePullRequestRequest(
                MergeStrategy.MERGE, "Merge PR #1");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));

        assertThrows(UnprocessableEntityException.class, () ->
                pullRequestService.mergePullRequest("owner", "repo", 1, request, userId.toString(), "merger", null));
    }

    @Test
    void debeLanzarExcepcionAlMergearSinAprobacion() {
        MergePullRequestRequest request = new MergePullRequestRequest(
                MergeStrategy.MERGE, "Merge PR #1");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(false);

        assertThrows(UnprocessableEntityException.class, () ->
                pullRequestService.mergePullRequest("owner", "repo", 1, request, userId.toString(), "merger", null));
    }

    @Test
    void debeLanzarExcepcionAlMergearConCambiosSolicitados() {
        MergePullRequestRequest request = new MergePullRequestRequest(
                MergeStrategy.MERGE, "Merge PR #1");

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(true);
        when(pullRequestDao.countChangesRequestedReviews(pullRequest)).thenReturn(1);

        assertThrows(UnprocessableEntityException.class, () ->
                pullRequestService.mergePullRequest("owner", "repo", 1, request, userId.toString(), "merger", null));
    }

    @Test
    void debeIndicarNoMergeableSiNoEstaAbierto() {
        pullRequest.setStatus(PrStatus.CLOSED);

        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(true);
        when(pullRequestDao.countChangesRequestedReviews(pullRequest)).thenReturn(0);

        PullRequestMergeabilityResponse result =
                pullRequestService.getPullRequestMergeability("owner", "repo", 1);

        assertFalse(result.mergeable());
        assertEquals("El pull request no esta abierto", result.reason());
    }

    @Test
    void debeIndicarNoMergeableSiNoTieneAprobacion() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(false);
        when(pullRequestDao.countChangesRequestedReviews(pullRequest)).thenReturn(0);

        PullRequestMergeabilityResponse result =
                pullRequestService.getPullRequestMergeability("owner", "repo", 1);

        assertFalse(result.mergeable());
        assertEquals("El pull request necesita al menos una aprobacion", result.reason());
    }

    @Test
    void debeIndicarNoMergeableSiTieneCambiosSolicitados() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.of(pullRequest));
        when(pullRequestDao.hasApprovedReview(pullRequest)).thenReturn(true);
        when(pullRequestDao.countChangesRequestedReviews(pullRequest)).thenReturn(1);

        PullRequestMergeabilityResponse result =
                pullRequestService.getPullRequestMergeability("owner", "repo", 1);

        assertFalse(result.mergeable());
        assertEquals("El pull request tiene solicitudes de cambios pendientes", result.reason());
    }

    @Test
    void debeLanzarExcepcionSiPullRequestNoExiste() {
        when(repositoryDao.findByOwnerAndName("owner", "repo"))
                .thenReturn(Optional.of(repository));
        when(pullRequestDao.findByRepositoryAndNumber(repository, 1))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                pullRequestService.getPullRequest("owner", "repo", 1));
    }
}
