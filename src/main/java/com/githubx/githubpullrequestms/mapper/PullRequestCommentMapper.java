package com.githubx.githubpullrequestms.mapper;

import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestCommentResponse;
import com.githubx.githubpullrequestms.model.PullRequestCommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PullRequestCommentMapper {

    @Mapping(target = "pullRequestId", source = "pullRequest.id")
    @Mapping(target = "author", source = "entity", qualifiedByName = "toAuthorSummary")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "formatInstant")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "formatInstant")
    PullRequestCommentResponse toResponse(PullRequestCommentEntity entity);

    List<PullRequestCommentResponse> toResponseList(List<PullRequestCommentEntity> entities);

    @Named("toAuthorSummary")
    default AuthorSummaryResponse toAuthorSummary(PullRequestCommentEntity entity) {
        return new AuthorSummaryResponse(entity.getAuthorId(), entity.getAuthorUsername());
    }

    @Named("formatInstant")
    default String formatInstant(Instant instant) {
        if (instant == null) return null;
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }
}
