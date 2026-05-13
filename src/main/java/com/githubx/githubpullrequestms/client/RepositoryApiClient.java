package com.githubx.githubpullrequestms.client;

import com.githubx.githubpullrequestms.client.dto.ExternalRepositoryDto;
import com.githubx.githubpullrequestms.client.dto.MergeRequest;
import com.githubx.githubpullrequestms.client.dto.MergeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Slf4j
@Component
public class RepositoryApiClient {

    private final RestClient restClient;
    private final String baseUrl;

    public RepositoryApiClient(
            @Value("${app.services.repository-ms.url:http://localhost:8090}") String baseUrl) {
        this.baseUrl = baseUrl;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        log.info("RepositoryApiClient initialized with baseUrl: {}", baseUrl);
    }

    public Optional<ExternalRepositoryDto> getRepository(String owner, String repo, String authToken) {
        String uri = "/v1/repos/" + owner + "/" + repo;
        log.info("Fetching repository from repository-ms: {} -> {}{}", owner + "/" + repo, baseUrl, uri);

        try {
            var requestBuilder = restClient.get().uri(uri);

            // Solo agregar token si existe
            if (authToken != null && !authToken.isEmpty()) {
                requestBuilder = requestBuilder.header("Authorization", "Bearer " + authToken);
            }

            ExternalRepositoryDto response = requestBuilder.retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, clientResponse) -> {
                        log.warn("Repository not found in repository-ms: {}/{}, status: {}",
                                owner, repo, clientResponse.getStatusCode());
                        throw new RuntimeException("Repository not found: " + owner + "/" + repo);
                    })
                    .body(ExternalRepositoryDto.class);

            log.info("Repository fetched successfully: {}", response);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            log.error("Error fetching repository from repository-ms: {}/{} - {}", owner, repo, e.getMessage());
            return Optional.empty();
        }
    }

    public MergeResponse mergeBranches(String owner, String repo, MergeRequest request, String authToken) {
        String uri = "/repos/" + owner + "/" + repo + "/merge";
        log.info("Merging branches in repository-ms: {}/{}, source: {}, target: {}",
                owner, repo, request.sourceBranch(), request.targetBranch());

        try {
            var requestBuilder = restClient.post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request);

            if (authToken != null && !authToken.isEmpty()) {
                requestBuilder = requestBuilder.header("Authorization", "Bearer " + authToken);
            }

            MergeResponse response = requestBuilder.retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, clientResponse) -> {
                        log.warn("Merge failed in repository-ms: {}/{}, status: {}",
                                owner, repo, clientResponse.getStatusCode());
                        throw new RuntimeException("Merge failed: " + clientResponse.getStatusCode());
                    })
                    .body(MergeResponse.class);

            log.info("Merge completed: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error merging branches in repository-ms: {}/{} - {}", owner, repo, e.getMessage());
            throw new RuntimeException("Merge failed: " + e.getMessage(), e);
        }
    }
}
