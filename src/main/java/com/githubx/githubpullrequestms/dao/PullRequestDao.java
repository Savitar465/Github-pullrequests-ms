package com.githubx.githubpullrequestms.dao;

import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PullRequestDao extends JpaRepository<PullRequestEntity, UUID> {

    List<PullRequestEntity> findByRepositoryOrderByCreatedAtDesc(RepositoryEntity repository);

    List<PullRequestEntity> findByRepositoryAndStatusOrderByCreatedAtDesc(
            RepositoryEntity repository, PrStatus status);

    Optional<PullRequestEntity> findByRepositoryAndNumber(RepositoryEntity repository, Integer number);

    @Query("SELECT COALESCE(MAX(pr.number), 0) + 1 FROM PullRequestEntity pr " +
           "WHERE pr.repository = :repo")
    Integer getNextPrNumber(@Param("repo") RepositoryEntity repository);

    @Query("SELECT COUNT(r) > 0 FROM PullRequestReviewEntity r " +
           "WHERE r.pullRequest = :pr AND r.decision = 'APPROVED'")
    boolean hasApprovedReview(@Param("pr") PullRequestEntity pullRequest);

    @Query("SELECT COUNT(r) FROM PullRequestReviewEntity r " +
           "WHERE r.pullRequest = :pr AND r.decision = 'CHANGES_REQUESTED'")
    int countChangesRequestedReviews(@Param("pr") PullRequestEntity pullRequest);

    @Query("SELECT pr FROM PullRequestEntity pr WHERE pr.repository = :repo " +
           "AND (LOWER(pr.title) LIKE :pattern OR LOWER(pr.description) LIKE :pattern) " +
           "ORDER BY pr.createdAt DESC")
    Page<PullRequestEntity> searchByTitleOrDescription(
            @Param("repo") RepositoryEntity repository,
            @Param("pattern") String pattern,
            Pageable pageable);

    @Query("SELECT pr FROM PullRequestEntity pr WHERE pr.repository = :repo " +
           "AND pr.status = :status " +
           "AND (LOWER(pr.title) LIKE :pattern OR LOWER(pr.description) LIKE :pattern) " +
           "ORDER BY pr.createdAt DESC")
    Page<PullRequestEntity> searchByTitleOrDescriptionAndStatus(
            @Param("repo") RepositoryEntity repository,
            @Param("pattern") String pattern,
            @Param("status") PrStatus status,
            Pageable pageable);
}
