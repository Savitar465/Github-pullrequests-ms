package com.githubx.githubpullrequestms.util.errorhandling;

import org.springframework.http.HttpStatus;

public class EntityConflictException extends BusinessException {

    public EntityConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
