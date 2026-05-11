package com.githubx.githubpullrequestms.util.errorhandling;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsTest {

    @Test
    void entityNotFoundExceptionDebeCrearMensajeCorrecto() {
        EntityNotFoundException ex = new EntityNotFoundException("Usuario", "123");

        assertEquals("Usuario no encontrado: 123", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void entityNotFoundExceptionConObjetoIdentificador() {
        EntityNotFoundException ex = new EntityNotFoundException("PullRequest", "#42");

        assertTrue(ex.getMessage().contains("#42"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void badRequestExceptionDebeCrearMensajeCorrecto() {
        BadRequestException ex = new BadRequestException("Datos invalidos");

        assertEquals("Datos invalidos", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void entityConflictExceptionDebeCrearMensajeCorrecto() {
        EntityConflictException ex = new EntityConflictException("El recurso ya existe");

        assertEquals("El recurso ya existe", ex.getMessage());
        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void forbiddenExceptionDebeCrearMensajeCorrecto() {
        ForbiddenException ex = new ForbiddenException("Acceso no permitido");

        assertEquals("Acceso no permitido", ex.getMessage());
        assertEquals(HttpStatus.FORBIDDEN, ex.getStatus());
    }

    @Test
    void unprocessableEntityExceptionDebeCrearMensajeCorrecto() {
        UnprocessableEntityException ex = new UnprocessableEntityException("No se puede procesar");

        assertEquals("No se puede procesar", ex.getMessage());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, ex.getStatus());
    }

    @Test
    void errorResponseDebeCrearseCorrectamente() {
        ErrorResponse error = new ErrorResponse(404, "Not Found", "Recurso no encontrado", "/api/test");

        assertEquals(404, error.status());
        assertEquals("Not Found", error.error());
        assertEquals("Recurso no encontrado", error.message());
        assertEquals("/api/test", error.path());
        assertNull(error.fieldErrors());
    }

    @Test
    void errorResponseConFieldErrorsDebeCrearseCorrectamente() {
        var fieldErrors = java.util.List.of(
                new ErrorResponse.FieldError("name", "must not be null"),
                new ErrorResponse.FieldError("email", "invalid format")
        );
        ErrorResponse error = new ErrorResponse(400, "Bad Request", "Validation error", "/api/test", fieldErrors);

        assertEquals(400, error.status());
        assertNotNull(error.fieldErrors());
        assertEquals(2, error.fieldErrors().size());
        assertEquals("name", error.fieldErrors().get(0).field());
        assertEquals("must not be null", error.fieldErrors().get(0).message());
    }

    @Test
    void fieldErrorDebeCrearseCorrectamente() {
        ErrorResponse.FieldError fieldError = new ErrorResponse.FieldError("username", "is required");

        assertEquals("username", fieldError.field());
        assertEquals("is required", fieldError.message());
    }
}
