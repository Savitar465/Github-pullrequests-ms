package com.githubx.githubpullrequestms.service.implementacion;

import com.githubx.githubpullrequestms.client.RepositoryApiClient;
import com.githubx.githubpullrequestms.client.dto.MergeRequest;
import com.githubx.githubpullrequestms.client.dto.MergeResponse;
import com.githubx.githubpullrequestms.dao.PullRequestDao;
import com.githubx.githubpullrequestms.dao.PullRequestReviewDao;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.dto.response.SearchPullRequestsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.githubx.githubpullrequestms.mapper.PullRequestMapper;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.PullRequestReviewEntity;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import com.githubx.githubpullrequestms.service.contratos.PullRequestService;
import com.githubx.githubpullrequestms.util.errorhandling.BadRequestException;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import com.githubx.githubpullrequestms.util.errorhandling.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PullRequestServiceImpl implements PullRequestService {

    private final PullRequestDao pullRequestDao;
    private final PullRequestReviewDao reviewDao;
    private final PullRequestMapper pullRequestMapper;
    private final RepositorySyncService repositorySyncService;
    private final RepositoryApiClient repositoryApiClient;

    @Override
    public ListPullRequestsResponse listPullRequests(String owner, String repo, PrStatus status) {
        RepositoryEntity repository = getRepository(owner, repo);

        List<PullRequestEntity> pullRequests = status == null
                ? pullRequestDao.findByRepositoryOrderByCreatedAtDesc(repository)
                : pullRequestDao.findByRepositoryAndStatusOrderByCreatedAtDesc(repository, status);

        List<PullRequestResponse> responses = pullRequestMapper.toResponseList(pullRequests);
        return new ListPullRequestsResponse(responses);
    }

    @Override
    public SearchPullRequestsResponse searchPullRequests(String owner, String repo, String query,
            PrStatus status, int page, int perPage) {
        RepositoryEntity repository = getRepository(owner, repo);
        PageRequest pageRequest = PageRequest.of(page - 1, perPage);
        String searchPattern = "%" + query.toLowerCase() + "%";

        Page<PullRequestEntity> prPage = status == null
                ? pullRequestDao.searchByTitleOrDescription(repository, searchPattern, pageRequest)
                : pullRequestDao.searchByTitleOrDescriptionAndStatus(repository, searchPattern, status, pageRequest);

        List<PullRequestResponse> responses = pullRequestMapper.toResponseList(prPage.getContent());

        SearchPullRequestsResponse.PaginationInfo pagination = new SearchPullRequestsResponse.PaginationInfo(
                page, perPage, prPage.getTotalElements(), prPage.getTotalPages());

        return new SearchPullRequestsResponse(responses, pagination);
    }

    @Override
    @Transactional
    public PullRequestResponse createPullRequest(String owner, String repo,
            CreatePullRequestRequest request, String currentUserId, String currentUsername) {
        RepositoryEntity repository = getRepository(owner, repo);

        if (request.sourceBranch().equals(request.targetBranch())) {
            throw new BadRequestException("La rama origen y destino no pueden ser iguales");
        }

        Integer nextNumber = pullRequestDao.getNextPrNumber(repository);

        PullRequestEntity pr = PullRequestEntity.builder()
                .repository(repository)
                .number(nextNumber)
                .title(request.title())
                .description(request.description())
                .sourceBranch(request.sourceBranch())
                .targetBranch(request.targetBranch())
                .authorId(UUID.fromString(currentUserId))
                .authorUsername(currentUsername)
                .status(PrStatus.OPEN)
                .hasConflicts(false)
                .commitsCount(0)
                .build();

        PullRequestEntity saved = pullRequestDao.save(pr);
        return pullRequestMapper.toResponse(saved);
    }

    @Override
    public PullRequestResponse getPullRequest(String owner, String repo, Integer prNumber) {
        RepositoryEntity repository = getRepository(owner, repo);
        PullRequestEntity pr = getPullRequestEntity(repository, prNumber);
        return pullRequestMapper.toResponse(pr);
    }

    @Override
    @Transactional
    public PullRequestResponse closePullRequest(String owner, String repo, Integer prNumber,
            String currentUserId, String currentUsername) {
        RepositoryEntity repository = getRepository(owner, repo);
        PullRequestEntity pr = getPullRequestEntity(repository, prNumber);

        if (pr.getStatus() != PrStatus.OPEN) {
            throw new BadRequestException("Solo se pueden cerrar pull requests abiertos");
        }

        pr.setStatus(PrStatus.CLOSED);

        PullRequestEntity closed = pullRequestDao.save(pr);
        return pullRequestMapper.toResponse(closed);
    }

    @Override
    @Transactional
    public PullRequestResponse reviewPullRequest(String owner, String repo, Integer prNumber,
            ReviewPullRequestRequest request, String currentUserId, String currentUsername) {
        RepositoryEntity repository = getRepository(owner, repo);
        PullRequestEntity pr = getPullRequestEntity(repository, prNumber);

        if (pr.getStatus() != PrStatus.OPEN) {
            throw new BadRequestException("Solo se pueden revisar pull requests abiertos");
        }

        PullRequestReviewEntity review = PullRequestReviewEntity.builder()
                .pullRequest(pr)
                .reviewerId(UUID.fromString(currentUserId))
                .reviewerUsername(currentUsername)
                .decision(request.decision())
                .comment(request.comment())
                .build();

        reviewDao.save(review);
        return pullRequestMapper.toResponse(pr);
    }

    @Override
    @Transactional
    public PullRequestResponse mergePullRequest(String owner, String repo, Integer prNumber,
            MergePullRequestRequest request, String currentUserId, String currentUsername) {
        RepositoryEntity repository = getRepository(owner, repo);
        PullRequestEntity pr = getPullRequestEntity(repository, prNumber);

        if (pr.getStatus() != PrStatus.OPEN) {
            throw new BadRequestException("Solo se pueden mergear pull requests abiertos");
        }

        if (pr.getHasConflicts()) {
            throw new UnprocessableEntityException("El pull request tiene conflictos que deben resolverse");
        }

        if (!pullRequestDao.hasApprovedReview(pr)) {
            throw new UnprocessableEntityException("El pull request necesita al menos una aprobacion");
        }

        if (pullRequestDao.countChangesRequestedReviews(pr) > 0) {
            throw new UnprocessableEntityException("El pull request tiene solicitudes de cambios pendientes");
        }

        // Ejecutar el merge real en el repositorio Git
        String commitMessage = request.commitMessage() != null ? request.commitMessage() :
                "Merge pull request #" + prNumber + " from " + pr.getSourceBranch();

        MergeRequest mergeRequest = new MergeRequest(
                pr.getSourceBranch(),
                pr.getTargetBranch(),
                request.strategy() != null ? request.strategy().name().toLowerCase() : "merge",
                commitMessage,
                currentUsername,
                currentUsername + "@githubclone.local"
        );

        try {
            MergeResponse mergeResponse = repositoryApiClient.mergeBranches(owner, repo, mergeRequest, null);

            if (!mergeResponse.success()) {
                throw new UnprocessableEntityException("Error al ejecutar el merge: " + mergeResponse.message());
            }
        } catch (Exception e) {
            throw new UnprocessableEntityException("Error al ejecutar el merge en el repositorio: " + e.getMessage());
        }

        // Actualizar el estado del PR en la base de datos
        pr.setStatus(PrStatus.MERGED);
        pr.setMergedAt(Instant.now());
        pr.setMergedById(UUID.fromString(currentUserId));
        pr.setMergedByUsername(currentUsername);

        PullRequestEntity merged = pullRequestDao.save(pr);
        return pullRequestMapper.toResponse(merged);
    }

    @Override
    public PullRequestMergeabilityResponse getPullRequestMergeability(
            String owner, String repo, Integer prNumber) {
        RepositoryEntity repository = getRepository(owner, repo);
        PullRequestEntity pr = getPullRequestEntity(repository, prNumber);

        boolean hasApproval = pullRequestDao.hasApprovedReview(pr);
        boolean hasChangesRequested = pullRequestDao.countChangesRequestedReviews(pr) > 0;

        boolean mergeable = pr.getStatus() == PrStatus.OPEN
                && !pr.getHasConflicts()
                && hasApproval
                && !hasChangesRequested;

        String reason = null;
        if (!mergeable) {
            if (pr.getStatus() != PrStatus.OPEN) {
                reason = "El pull request no esta abierto";
            } else if (pr.getHasConflicts()) {
                reason = "El pull request tiene conflictos";
            } else if (!hasApproval) {
                reason = "El pull request necesita al menos una aprobacion";
            } else if (hasChangesRequested) {
                reason = "El pull request tiene solicitudes de cambios pendientes";
            }
        }

        return new PullRequestMergeabilityResponse(prNumber, mergeable, pr.getHasConflicts(), reason);
    }

    private RepositoryEntity getRepository(String owner, String repo) {
        return repositorySyncService.getOrSyncRepository(owner, repo);
    }

    private PullRequestEntity getPullRequestEntity(RepositoryEntity repository, Integer prNumber) {
        return pullRequestDao.findByRepositoryAndNumber(repository, prNumber)
                .orElseThrow(() -> new EntityNotFoundException("PullRequest", "#" + prNumber));
    }
}
