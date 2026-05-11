package com.githubx.githubpullrequestms.model;

import com.githubx.githubpullrequestms.model.enums.RepoVisibility;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryEntityTest {

    @Test
    void debeCrearRepositorioConBuilder() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .id(1L)
                .owner("owner")
                .name("repo")
                .description("A test repository")
                .visibility(RepoVisibility.PUBLIC)
                .defaultBranch("main")
                .build();

        assertEquals(1L, repo.getId());
        assertEquals("owner", repo.getOwner());
        assertEquals("repo", repo.getName());
        assertEquals("A test repository", repo.getDescription());
        assertEquals(RepoVisibility.PUBLIC, repo.getVisibility());
        assertEquals("main", repo.getDefaultBranch());
    }

    @Test
    void debeUsarValoresPorDefecto() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("owner")
                .name("repo")
                .build();

        assertEquals(RepoVisibility.PRIVATE, repo.getVisibility());
        assertEquals("main", repo.getDefaultBranch());
    }

    @Test
    void debeSetearYGetearPropiedades() {
        RepositoryEntity repo = new RepositoryEntity();
        Instant now = Instant.now();

        repo.setId(5L);
        repo.setOwner("newowner");
        repo.setName("newrepo");
        repo.setDescription("New description");
        repo.setVisibility(RepoVisibility.PUBLIC);
        repo.setDefaultBranch("develop");
        repo.setCreatedAt(now);
        repo.setUpdatedAt(now);

        assertEquals(5L, repo.getId());
        assertEquals("newowner", repo.getOwner());
        assertEquals("newrepo", repo.getName());
        assertEquals("New description", repo.getDescription());
        assertEquals(RepoVisibility.PUBLIC, repo.getVisibility());
        assertEquals("develop", repo.getDefaultBranch());
        assertEquals(now, repo.getCreatedAt());
        assertEquals(now, repo.getUpdatedAt());
    }

    @Test
    void debeCrearRepositorioPrivado() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("owner")
                .name("private-repo")
                .visibility(RepoVisibility.PRIVATE)
                .build();

        assertEquals(RepoVisibility.PRIVATE, repo.getVisibility());
    }

    @Test
    void debeCrearRepositorioPublico() {
        RepositoryEntity repo = RepositoryEntity.builder()
                .owner("org")
                .name("public-repo")
                .visibility(RepoVisibility.PUBLIC)
                .build();

        assertEquals(RepoVisibility.PUBLIC, repo.getVisibility());
    }
}
