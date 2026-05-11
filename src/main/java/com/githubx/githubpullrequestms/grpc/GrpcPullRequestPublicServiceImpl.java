package com.githubx.githubpullrequestms.grpc;

import com.githubx.githubpullrequestms.service.contratos.PullRequestCommentService;
import com.githubx.githubpullrequestms.service.contratos.PullRequestService;
import com.githubx.githubpullrequestms.util.errorhandling.EntityNotFoundException;
import com.githubx.grpc.proto.GetPullRequestMergeabilityRequest;
import com.githubx.grpc.proto.GetPullRequestMergeabilityResponse;
import com.githubx.grpc.proto.GetPullRequestRequest;
import com.githubx.grpc.proto.GetPullRequestResponse;
import com.githubx.grpc.proto.ListPullRequestCommentsRequest;
import com.githubx.grpc.proto.ListPullRequestCommentsResponse;
import com.githubx.grpc.proto.ListPullRequestsRequest;
import com.githubx.grpc.proto.ListPullRequestsResponse;
import com.githubx.grpc.proto.PullRequestPublicServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GrpcPullRequestPublicServiceImpl
        extends PullRequestPublicServiceGrpc.PullRequestPublicServiceImplBase {

    private final PullRequestService pullRequestService;
    private final PullRequestCommentService pullRequestCommentService;
    private final GrpcProtoMapper mapper;

    @Override
    public void getPullRequest(GetPullRequestRequest req,
                               StreamObserver<GetPullRequestResponse> obs) {
        try {
            obs.onNext(GetPullRequestResponse.newBuilder()
                    .setPullRequest(mapper.toProto(
                            pullRequestService.getPullRequest(req.getOwner(), req.getRepo(), req.getPrNumber())))
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void listPullRequests(ListPullRequestsRequest req,
                                 StreamObserver<ListPullRequestsResponse> obs) {
        try {
            var status = req.getStatus() == com.githubx.grpc.proto.PrStatus.PR_STATUS_UNSPECIFIED
                    ? null
                    : mapper.fromProtoStatus(req.getStatus());
            var result = pullRequestService.listPullRequests(req.getOwner(), req.getRepo(), status);

            obs.onNext(ListPullRequestsResponse.newBuilder()
                    .addAllPullRequests(result.pullRequests().stream().map(mapper::toProto).toList())
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void listPullRequestComments(ListPullRequestCommentsRequest req,
                                        StreamObserver<ListPullRequestCommentsResponse> obs) {
        try {
            var result = pullRequestCommentService.listComments(
                    req.getOwner(), req.getRepo(), req.getPrNumber());
            obs.onNext(ListPullRequestCommentsResponse.newBuilder()
                    .addAllComments(result.comments().stream().map(mapper::toProtoComment).toList())
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getPullRequestMergeability(GetPullRequestMergeabilityRequest req,
                                           StreamObserver<GetPullRequestMergeabilityResponse> obs) {
        try {
            obs.onNext(GetPullRequestMergeabilityResponse.newBuilder()
                    .setMergeability(mapper.toProtoMergeability(
                            pullRequestService.getPullRequestMergeability(
                                    req.getOwner(), req.getRepo(), req.getPrNumber())))
                    .build());
            obs.onCompleted();
        } catch (EntityNotFoundException e) {
            obs.onError(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            obs.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }
}
