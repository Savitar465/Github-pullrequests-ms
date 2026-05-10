package com.githubx.githubpullrequestms.dto.request;

import com.githubx.githubpullrequestms.model.enums.ReviewDecision;
import jakarta.validation.constraints.NotNull;

public record ReviewPullRequestRequest(
        @NotNull(message = "La decision es requerida")
        ReviewDecision decision,

        String comment
) {}
