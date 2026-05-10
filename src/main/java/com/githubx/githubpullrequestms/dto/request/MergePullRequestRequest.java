package com.githubx.githubpullrequestms.dto.request;

import com.githubx.githubpullrequestms.model.enums.MergeStrategy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MergePullRequestRequest(
        @NotNull(message = "La estrategia de merge es requerida")
        MergeStrategy strategy,

        @Size(max = 500, message = "El mensaje del commit no puede exceder 500 caracteres")
        String commitMessage
) {}
