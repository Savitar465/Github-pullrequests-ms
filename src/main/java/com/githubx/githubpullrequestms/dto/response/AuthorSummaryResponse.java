package com.githubx.githubpullrequestms.dto.response;

import java.util.UUID;

public record AuthorSummaryResponse(
        UUID id,
        String username
) {}
