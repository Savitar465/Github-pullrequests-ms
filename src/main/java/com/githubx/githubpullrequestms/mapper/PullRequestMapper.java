package com.githubx.githubpullrequestms.mapper;

import com.githubx.githubpullrequestms.dto.response.AuthorSummaryResponse;
import com.githubx.githubpullrequestms.dto.response.PullRequestResponse;
import com.githubx.githubpullrequestms.model.PullRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PullRequestMapper {

    @Mapping(target = "repoId", expression = "java(entity.getRepository().getId().toString())")
    @Mapping(target = "author", source = "entity", qualifiedByName = "toAuthorSummary")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "formatInstant")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "formatInstant")
    @Mapping(target = "mergedAt", source = "mergedAt", qualifiedByName = "formatInstant")
    PullRequestResponse toResponse(PullRequestEntity entity);

    List<PullRequestResponse> toResponseList(List<PullRequestEntity> entities);

    @Named("toAuthorSummary")
    default AuthorSummaryResponse toAuthorSummary(PullRequestEntity entity) {
        return new AuthorSummaryResponse(entity.getAuthorId(), entity.getAuthorUsername());
    }

    @Named("formatInstant")
    default String formatInstant(Instant instant) {
        if (instant == null) return null;
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }
}
