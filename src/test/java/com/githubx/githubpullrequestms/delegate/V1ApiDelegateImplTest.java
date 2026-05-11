package com.githubx.githubpullrequestms.delegate;

import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestCommentsResponse;
import com.githubx.githubpullrequestms.dto.response.ListPullRequestsResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestMergeabilityResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.mapper.SmithyDtoMapper;
import com.githubx.githubpullrequestms.model.enums.PrStatus;
import com.githubx.githubpullrequestms.service.contratos.PullRequestCommentService;
import com.githubx.githubpullrequestms.service.contratos.PullRequestService;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.CreatePullRequestCommentBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestCommentsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.ListPullRequestsBody;
import com.smithy.g.pullrequest.server.pullrequest.model.MergePullRequestBody;
import com.smithy.g.pullrequest.server.pullrequest.model.MergeStrategy;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestCommentDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.PullRequestMergeabilityDTO;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewDecision;
import com.smithy.g.pullrequest.server.pullrequest.model.ReviewPullRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class V1ApiDelegateImplTest {

    @Mock
    private PullRequestService pullRequestService;

    @Mock
    private PullRequestCommentService pullRequestCommentService;

    @Mock
    private SmithyDtoMapper smithyDtoMapper;

    @InjectMocks
    private V1ApiDelegateImpl v1ApiDelegate;

    private UUID userId;
    private PullRequestResponse pullRequestResponse;
    private PullRequestDTO pullRequestDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        AuthorSummaryResponse authorSummary = new AuthorSummaryResponse(userId, "testuser");

        pullRequestResponse = new PullRequestResponse(
                UUID.randomUUID(), "1", 1, "Test PR", "Description",
                "feature", "main", authorSummary, PrStatus.OPEN,
                false, 1, "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z", null);

        pullRequestDTO = new PullRequestDTO();
        pullRequestDTO.setId(pullRequestResponse.id().toString());
        pullRequestDTO.setTitle("Test PR");
    }

    @Test
    void debeListarPullRequests() {
        ListPullRequestsResponse serviceResponse = new ListPullRequestsResponse(Collections.emptyList());
        ListPullRequestsBody body = new ListPullRequestsBody();
        body.setPullRequests(Collections.emptyList());

        when(smithyDtoMapper.mapSmithyPrStatus(any())).thenReturn(null);
        when(pullRequestService.listPullRequests("owner", "repo", null)).thenReturn(serviceResponse);
        when(smithyDtoMapper.toListPullRequestsBody(serviceResponse)).thenReturn(body);

        ResponseEntity<ListPullRequestsBody> result = v1ApiDelegate.listPullRequests("owner", "repo", null);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(pullRequestService).listPullRequests("owner", "repo", null);
    }

    @Test
    void debeListarPullRequestsConStatus() {
        ListPullRequestsResponse serviceResponse = new ListPullRequestsResponse(Collections.emptyList());
        ListPullRequestsBody body = new ListPullRequestsBody();

        when(smithyDtoMapper.mapSmithyPrStatus(com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.OPEN))
                .thenReturn(PrStatus.OPEN);
        when(pullRequestService.listPullRequests("owner", "repo", PrStatus.OPEN)).thenReturn(serviceResponse);
        when(smithyDtoMapper.toListPullRequestsBody(serviceResponse)).thenReturn(body);

        ResponseEntity<ListPullRequestsBody> result = v1ApiDelegate.listPullRequests(
                "owner", "repo", com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.OPEN);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(smithyDtoMapper).mapSmithyPrStatus(com.smithy.g.pullrequest.server.pullrequest.model.PrStatus.OPEN);
    }

    @Test
    void debeCrearPullRequest() {
        setupSecurityContext();

        CreatePullRequestBody createBody = new CreatePullRequestBody();
        createBody.setTitle("New PR");
        createBody.setDescription("Description");
        createBody.setSourceBranch("feature");
        createBody.setTargetBranch("main");

        CreatePullRequestRequest request = new CreatePullRequestRequest(
                "New PR", "Description", "feature", "main");

        when(smithyDtoMapper.toCreatePullRequestRequest(createBody)).thenReturn(request);
        when(pullRequestService.createPullRequest(eq("owner"), eq("repo"), eq(request), anyString(), anyString()))
                .thenReturn(pullRequestResponse);
        when(smithyDtoMapper.toPullRequestDTO(pullRequestResponse)).thenReturn(pullRequestDTO);

        ResponseEntity<PullRequestDTO> result = v1ApiDelegate.createPullRequest("owner", "repo", createBody);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Test PR", result.getBody().getTitle());
    }

    @Test
    void debeObtenerPullRequest() {
        when(pullRequestService.getPullRequest("owner", "repo", 1)).thenReturn(pullRequestResponse);
        when(smithyDtoMapper.toPullRequestDTO(pullRequestResponse)).thenReturn(pullRequestDTO);

        ResponseEntity<PullRequestDTO> result = v1ApiDelegate.getPullRequest(
                "owner", "repo", BigDecimal.ONE);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(pullRequestService).getPullRequest("owner", "repo", 1);
    }

    @Test
    void debeRevisarPullRequest() {
        setupSecurityContext();

        ReviewPullRequestBody reviewBody = new ReviewPullRequestBody();
        reviewBody.setDecision(ReviewDecision.APPROVED);
        reviewBody.setComment("LGTM");

        ReviewPullRequestRequest request = new ReviewPullRequestRequest(
                com.githubx.githubpullrequestms.model.enums.ReviewDecision.APPROVED, "LGTM");

        when(smithyDtoMapper.toReviewPullRequestRequest(reviewBody)).thenReturn(request);
        when(pullRequestService.reviewPullRequest(eq("owner"), eq("repo"), eq(1), eq(request), anyString(), anyString()))
                .thenReturn(pullRequestResponse);
        when(smithyDtoMapper.toPullRequestDTO(pullRequestResponse)).thenReturn(pullRequestDTO);

        ResponseEntity<PullRequestDTO> result = v1ApiDelegate.reviewPullRequest(
                "owner", "repo", BigDecimal.ONE, reviewBody);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(pullRequestService).reviewPullRequest(eq("owner"), eq("repo"), eq(1), eq(request), anyString(), anyString());
    }

    @Test
    void debeMergearPullRequest() {
        setupSecurityContext();

        MergePullRequestBody mergeBody = new MergePullRequestBody();
        mergeBody.setStrategy(MergeStrategy.SQUASH);
        mergeBody.setCommitMessage("Merge commit");

        MergePullRequestRequest request = new MergePullRequestRequest(
                com.githubx.githubpullrequestms.model.enums.MergeStrategy.SQUASH, "Merge commit");

        when(smithyDtoMapper.toMergePullRequestRequest(mergeBody)).thenReturn(request);
        when(pullRequestService.mergePullRequest(eq("owner"), eq("repo"), eq(1), eq(request), anyString(), anyString()))
                .thenReturn(pullRequestResponse);
        when(smithyDtoMapper.toPullRequestDTO(pullRequestResponse)).thenReturn(pullRequestDTO);

        ResponseEntity<PullRequestDTO> result = v1ApiDelegate.mergePullRequest(
                "owner", "repo", BigDecimal.ONE, mergeBody);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(pullRequestService).mergePullRequest(eq("owner"), eq("repo"), eq(1), eq(request), anyString(), anyString());
    }

    @Test
    void debeObtenerMergeabilidad() {
        PullRequestMergeabilityResponse mergeabilityResponse = new PullRequestMergeabilityResponse(
                1, true, false, null);
        PullRequestMergeabilityDTO mergeabilityDTO = new PullRequestMergeabilityDTO();
        mergeabilityDTO.setPrNumber(BigDecimal.ONE);
        mergeabilityDTO.setMergeable(true);

        when(pullRequestService.getPullRequestMergeability("owner", "repo", 1))
                .thenReturn(mergeabilityResponse);
        when(smithyDtoMapper.toPullRequestMergeabilityDTO(mergeabilityResponse))
                .thenReturn(mergeabilityDTO);

        ResponseEntity<PullRequestMergeabilityDTO> result = v1ApiDelegate.getPullRequestMergeability(
                "owner", "repo", BigDecimal.ONE);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().getMergeable());
    }

    @Test
    void debeListarComentarios() {
        ListPullRequestCommentsResponse commentsResponse = new ListPullRequestCommentsResponse(Collections.emptyList());
        ListPullRequestCommentsBody body = new ListPullRequestCommentsBody();
        body.setComments(Collections.emptyList());

        when(pullRequestCommentService.listComments("owner", "repo", 1)).thenReturn(commentsResponse);
        when(smithyDtoMapper.toListPullRequestCommentsBody(commentsResponse)).thenReturn(body);

        ResponseEntity<ListPullRequestCommentsBody> result = v1ApiDelegate.listPullRequestComments(
                "owner", "repo", BigDecimal.ONE);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(pullRequestCommentService).listComments("owner", "repo", 1);
    }

    @Test
    void debeCrearComentario() {
        setupSecurityContext();

        CreatePullRequestCommentBody createBody = new CreatePullRequestCommentBody();
        createBody.setBody("Great code!");
        createBody.setFilePath("src/main.java");
        createBody.setLineNumber(BigDecimal.valueOf(10));

        CreatePullRequestCommentRequest request = new CreatePullRequestCommentRequest(
                "Great code!", "src/main.java", 10);

        PullRequestCommentResponse commentResponse = new PullRequestCommentResponse(
                UUID.randomUUID(), UUID.randomUUID(), "Great code!", "src/main.java", 10,
                new AuthorSummaryResponse(userId, "testuser"),
                "2026-01-01T00:00:00Z", "2026-01-01T00:00:00Z");

        PullRequestCommentDTO commentDTO = new PullRequestCommentDTO();
        commentDTO.setId(commentResponse.id().toString());
        commentDTO.setBody("Great code!");

        when(smithyDtoMapper.toCreatePullRequestCommentRequest(createBody)).thenReturn(request);
        when(pullRequestCommentService.createComment(eq("owner"), eq("repo"), eq(1), eq(request), anyString(), anyString()))
                .thenReturn(commentResponse);
        when(smithyDtoMapper.toPullRequestCommentDTO(commentResponse)).thenReturn(commentDTO);

        ResponseEntity<PullRequestCommentDTO> result = v1ApiDelegate.createPullRequestComment(
                "owner", "repo", BigDecimal.ONE, createBody);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Great code!", result.getBody().getBody());
    }

    @Test
    void debeUsarAnonymousCuandoNoHayAutenticacion() {
        SecurityContextHolder.clearContext();

        CreatePullRequestBody createBody = new CreatePullRequestBody();
        createBody.setTitle("New PR");

        CreatePullRequestRequest request = new CreatePullRequestRequest(
                "New PR", null, null, null);

        when(smithyDtoMapper.toCreatePullRequestRequest(createBody)).thenReturn(request);
        when(pullRequestService.createPullRequest(eq("owner"), eq("repo"), eq(request), anyString(), eq("anonymous")))
                .thenReturn(pullRequestResponse);
        when(smithyDtoMapper.toPullRequestDTO(pullRequestResponse)).thenReturn(pullRequestDTO);

        ResponseEntity<PullRequestDTO> result = v1ApiDelegate.createPullRequest("owner", "repo", createBody);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(pullRequestService).createPullRequest(eq("owner"), eq("repo"), eq(request), anyString(), eq("anonymous"));
    }

    @Test
    void debeUsarNameCuandoPreferredUsernameEsNull() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("sub")).thenReturn(userId.toString());
        when(jwt.getClaimAsString("preferred_username")).thenReturn(null);
        when(jwt.getClaimAsString("name")).thenReturn("John Doe");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        CreatePullRequestBody createBody = new CreatePullRequestBody();
        CreatePullRequestRequest request = new CreatePullRequestRequest(null, null, null, null);

        when(smithyDtoMapper.toCreatePullRequestRequest(createBody)).thenReturn(request);
        when(pullRequestService.createPullRequest(eq("owner"), eq("repo"), eq(request), eq(userId.toString()), eq("John Doe")))
                .thenReturn(pullRequestResponse);
        when(smithyDtoMapper.toPullRequestDTO(pullRequestResponse)).thenReturn(pullRequestDTO);

        v1ApiDelegate.createPullRequest("owner", "repo", createBody);

        verify(pullRequestService).createPullRequest(eq("owner"), eq("repo"), eq(request), eq(userId.toString()), eq("John Doe"));
    }

    @Test
    void debeUsarAnonymousCuandoPreferredUsernameYNameSonNull() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("sub")).thenReturn(userId.toString());
        when(jwt.getClaimAsString("preferred_username")).thenReturn(null);
        when(jwt.getClaimAsString("name")).thenReturn(null);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        CreatePullRequestBody createBody = new CreatePullRequestBody();
        CreatePullRequestRequest request = new CreatePullRequestRequest(null, null, null, null);

        when(smithyDtoMapper.toCreatePullRequestRequest(createBody)).thenReturn(request);
        when(pullRequestService.createPullRequest(eq("owner"), eq("repo"), eq(request), eq(userId.toString()), eq("anonymous")))
                .thenReturn(pullRequestResponse);
        when(smithyDtoMapper.toPullRequestDTO(pullRequestResponse)).thenReturn(pullRequestDTO);

        v1ApiDelegate.createPullRequest("owner", "repo", createBody);

        verify(pullRequestService).createPullRequest(eq("owner"), eq("repo"), eq(request), eq(userId.toString()), eq("anonymous"));
    }

    private void setupSecurityContext() {
        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("sub")).thenReturn(userId.toString());
        when(jwt.getClaimAsString("preferred_username")).thenReturn("testuser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
