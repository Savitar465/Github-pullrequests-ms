package com.githubx.githubpullrequestms.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreatePullRequestCommentRequest(
        @NotBlank(message = "El contenido del comentario es requerido")
        String body,

        String filePath,

        @Min(value = 1, message = "El numero de linea debe ser mayor a 0")
        Integer lineNumber
) {}
