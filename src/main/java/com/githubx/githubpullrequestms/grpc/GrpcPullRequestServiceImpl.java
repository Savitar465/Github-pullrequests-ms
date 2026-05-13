package com.githubx.githubpullrequestms.grpc;

import com.githubx.githubpullrequestms.dto.request.CreatePullRequestCommentRequest;
import com.githubx.githubpullrequestms.dto.request.CreatePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.MergePullRequestRequest;
import com.githubx.githubpullrequestms.dto.request.ReviewPullRequestRequest;
import com.githubx.githubpullrequestms.service.contratos.PullRequestCommentService;
import com.githubx.githubpullrequestms.service.contratos.PullRequestService;
import com.githubx.githubpullrequestms.util.errorhandling.EntityConflictException;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import com.githubx.githubpullrequestms.util.errorhandling.ForbiddenException;
import com.githubx.grpc.proto.CreatePullRequestCommentResponse;
import com.githubx.grpc.proto.CreatePullRequestResponse;
import com.githubx.grpc.proto.MergePullRequestResponse;
import com.githubx.grpc.proto.PullRequestServiceGrpc;
import com.githubx.grpc.proto.ReviewPullRequestResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class GrpcPullRequestServiceImpl extends PullRequestServiceGrpc.PullRequestServiceImplBase {

    private final PullRequestService pullRequestService;
    private final PullRequestCommentService pullRequestCommentService;
    private final GrpcProtoMapper mapper;

    @Override
    public void createPullRequest(com.githubx.grpc.proto.CreatePullRequestRequest req,
                                  StreamObserver<CreatePullRequestResponse> obs) {
        try {
            String[] userInfo = extractUserInfo();
            var request = new CreatePullRequestRequest(
                    req.getTitle(),
                    req.getDescription().isEmpty() ? null : req.getDescription(),
                    req.getSourceBranch(),
                    req.getTargetBranch()
            );
            obs.onNext(CreatePullRequestResponse.newBuilder()
                    .setPullRequest(mapper.toProto(
                            pullRequestService.createPullRequest(
                                    req.getOwner(), req.getRepo(), request, userInfo[0], userInfo[1])))
                    .build());
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void reviewPullRequest(com.githubx.grpc.proto.ReviewPullRequestRequest req,
                                  StreamObserver<ReviewPullRequestResponse> obs) {
        try {
            String[] userInfo = extractUserInfo();
            var request = new ReviewPullRequestRequest(
                    mapper.fromProtoReviewDecision(req.getDecision()),
                    req.getComment().isEmpty() ? null : req.getComment()
            );
            obs.onNext(ReviewPullRequestResponse.newBuilder()
                    .setPullRequest(mapper.toProto(
                            pullRequestService.reviewPullRequest(
                                    req.getOwner(), req.getRepo(), req.getPrNumber(),
                                    request, userInfo[0], userInfo[1])))
                    .build());
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void mergePullRequest(com.githubx.grpc.proto.MergePullRequestRequest req,
                                 StreamObserver<MergePullRequestResponse> obs) {
        try {
            String[] userInfo = extractUserInfo();
            String authToken = extractRawToken();
            var request = new MergePullRequestRequest(
                    mapper.fromProtoMergeStrategy(req.getStrategy()),
                    req.getCommitMessage().isEmpty() ? null : req.getCommitMessage()
            );
            obs.onNext(MergePullRequestResponse.newBuilder()
                    .setPullRequest(mapper.toProto(
                            pullRequestService.mergePullRequest(
                                    req.getOwner(), req.getRepo(), req.getPrNumber(),
                                    request, userInfo[0], userInfo[1], authToken)))
                    .build());
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    @Override
    public void createPullRequestComment(com.githubx.grpc.proto.CreatePullRequestCommentRequest req,
                                         StreamObserver<CreatePullRequestCommentResponse> obs) {
        try {
            String[] userInfo = extractUserInfo();
            var request = new CreatePullRequestCommentRequest(
                    req.getBody(),
                    req.getFilePath().isEmpty() ? null : req.getFilePath(),
                    req.getLineNumber() > 0 ? req.getLineNumber() : null
            );
            obs.onNext(CreatePullRequestCommentResponse.newBuilder()
                    .setComment(mapper.toProtoComment(
                            pullRequestCommentService.createComment(
                                    req.getOwner(), req.getRepo(), req.getPrNumber(),
                                    request, userInfo[0], userInfo[1])))
                    .build());
            obs.onCompleted();
        } catch (Exception e) {
            obs.onError(toStatus(e).asRuntimeException());
        }
    }

    // ─── Helpers ──────────────────────────────────────────────

    private String extractRawToken() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken().getTokenValue();
        }
        return null;
    }

    private String[] extractUserInfo() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            String userId = jwt.getClaimAsString("sub");
            String username = jwt.getClaimAsString("preferred_username");
            if (username == null) username = jwt.getClaimAsString("name");
            if (username == null) username = "anonymous";
            return new String[]{userId, username};
        }
        return new String[]{UUID.randomUUID().toString(), "anonymous"};
    }

    private Status toStatus(Exception e) {
        if (e instanceof EntityNotFoundException) return Status.NOT_FOUND.withDescription(e.getMessage());
        if (e instanceof EntityConflictException) return Status.ALREADY_EXISTS.withDescription(e.getMessage());
        if (e instanceof ForbiddenException) return Status.PERMISSION_DENIED.withDescription(e.getMessage());
        return Status.INTERNAL.withDescription(e.getMessage());
    }
}
