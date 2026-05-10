package com.githubx.githubpullrequestms.util.errorhandling;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String entityName, Object identifier) {
        super(String.format("%s no encontrado: %s", entityName, identifier), HttpStatus.NOT_FOUND);
    }
}
