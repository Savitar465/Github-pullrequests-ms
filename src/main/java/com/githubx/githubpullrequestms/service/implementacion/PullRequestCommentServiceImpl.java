package com.githubx.githubpullrequestms.service.implementacion;

import com.githubx.githubpullrequestms.dao.PullRequestCommentDao;
import com.githubx.githubpullrequestms.dao.PullRequestDao;
import com.githubx.githubpullrequestms.dao.RepositoryDao;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestCommentsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.mapper.PullRequestCommentMapper;
import com.githubx.githubpullrequestms.model.PullRequestCommentEntity;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.service.contratos.PullRequestCommentService;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PullRequestCommentServiceImpl implements PullRequestCommentService {

    private final PullRequestCommentDao commentDao;
    private final PullRequestDao pullRequestDao;
    private final RepositoryDao repositoryDao;
    private final PullRequestCommentMapper commentMapper;

    @Override
    public ListPullRequestCommentsResponse listComments(String owner, String repo, Integer prNumber) {
        RepositoryEntity repository = getRepository(owner, repo);
        PullRequestEntity pr = getPullRequestEntity(repository, prNumber);

        List<PullRequestCommentEntity> comments =
                commentDao.findByPullRequestOrderByCreatedAtAsc(pr);

        List<PullRequestCommentResponse> responses = commentMapper.toResponseList(comments);
        return new ListPullRequestCommentsResponse(responses);
    }

    @Override
    @Transactional
    public PullRequestCommentResponse createComment(String owner, String repo, Integer prNumber,
            CreatePullRequestCommentRequest request, String currentUserId, String currentUsername) {
        RepositoryEntity repository = getRepository(owner, repo);
        PullRequestEntity pr = getPullRequestEntity(repository, prNumber);

        PullRequestCommentEntity comment = PullRequestCommentEntity.builder()
                .pullRequest(pr)
                .body(request.body())
                .filePath(request.filePath())
                .lineNumber(request.lineNumber())
                .authorId(UUID.fromString(currentUserId))
                .authorUsername(currentUsername)
                .build();

        PullRequestCommentEntity saved = commentDao.save(comment);
        return commentMapper.toResponse(saved);
    }

    private RepositoryEntity getRepository(String owner, String repo) {
        return repositoryDao.findByOwnerAndName(owner, repo)
                .orElseThrow(() -> new EntityNotFoundException("Repositorio", owner + "/" + repo));
    }

    private PullRequestEntity getPullRequestEntity(RepositoryEntity repository, Integer prNumber) {
        return pullRequestDao.findByRepositoryAndNumber(repository, prNumber)
                .orElseThrow(() -> new EntityNotFoundException("PullRequest", "#" + prNumber));
    }
}
