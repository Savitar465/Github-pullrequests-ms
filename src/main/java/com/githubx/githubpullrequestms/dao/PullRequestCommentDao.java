package com.githubx.githubpullrequestms.dao;

import com.githubx.githubpullrequestms.model.PullRequestCommentEntity;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PullRequestCommentDao extends JpaRepository<PullRequestCommentEntity, UUID> {

    List<PullRequestCommentEntity> findByPullRequestOrderByCreatedAtAsc(PullRequestEntity pullRequest);

    List<PullRequestCommentEntity> findByPullRequestAndFilePathOrderByLineNumberAsc(
            PullRequestEntity pullRequest, String filePath);
}
