package com.githubx.githubpullrequestms.service.implementacion;

import com.githubx.githubpullrequestms.client.RepositoryApiClient;
import com.githubx.githubpullrequestms.client.dto.ExternalRepositoryDto;
import com.githubx.githubpullrequestms.dao.RepositoryDao;
import com.githubx.githubpullrequestms.model.RepositoryEntity;
import com.githubx.githubpullrequestms.model.enums.RepoVisibility;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepositorySyncService {

    private final RepositoryDao repositoryDao;
    private final RepositoryApiClient repositoryApiClient;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RepositoryEntity getOrSyncRepository(String owner, String repo) {
        return repositoryDao.findByOwnerAndName(owner, repo)
                .orElseGet(() -> syncFromExternalService(owner, repo));
    }

    private RepositoryEntity syncFromExternalService(String owner, String repo) {
        log.info("Repository {}/{} not found locally, fetching from repository-ms", owner, repo);

        String token = getCurrentToken();
        Optional<ExternalRepositoryDto> externalRepo = repositoryApiClient.getRepository(owner, repo, token);

        if (externalRepo.isEmpty()) {
            throw new EntityNotFoundException("Repositorio", owner + "/" + repo);
        }

        ExternalRepositoryDto dto = externalRepo.get();
        RepositoryEntity entity = RepositoryEntity.builder()
                .owner(dto.ownerUsername() != null ? dto.ownerUsername() : owner)
                .name(dto.name())
                .description(dto.description())
                .visibility(parseVisibility(dto.visibility()))
                .defaultBranch(dto.defaultBranch() != null ? dto.defaultBranch() : "main")
                .build();

        RepositoryEntity saved = repositoryDao.save(entity);
        log.info("Repository {}/{} synced successfully with local id: {}", owner, repo, saved.getId());
        return saved;
    }

    private RepoVisibility parseVisibility(String visibility) {
        if (visibility == null) {
            return RepoVisibility.PRIVATE;
        }
        try {
            return RepoVisibility.fromValue(visibility);
        } catch (IllegalArgumentException e) {
            return RepoVisibility.PRIVATE;
        }
    }

    private String getCurrentToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getCredentials() instanceof Jwt jwt) {
            return jwt.getTokenValue();
        }
        if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {
            return jwt.getTokenValue();
        }
        return "";
    }
}
