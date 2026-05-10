package com.githubx.githubpullrequestms.dao;

import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.PullRequestReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PullRequestReviewDao extends JpaRepository<PullRequestReviewEntity, UUID> {

    List<PullRequestReviewEntity> findByPullRequestOrderByCreatedAtDesc(PullRequestEntity pullRequest);

    Optional<PullRequestReviewEntity> findTopByPullRequestAndReviewerIdOrderByCreatedAtDesc(
            PullRequestEntity pullRequest, UUID reviewerId);
}
