package com.githubx.githubpullrequestms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePullRequestRequest(
        @NotBlank(message = "El titulo es requerido")
        @Size(max = 255, message = "El titulo no puede exceder 255 caracteres")
        String title,

        String description,

        @NotBlank(message = "La rama origen es requerida")
        String sourceBranch,

        @NotBlank(message = "La rama destino es requerida")
        String targetBranch
) {}
