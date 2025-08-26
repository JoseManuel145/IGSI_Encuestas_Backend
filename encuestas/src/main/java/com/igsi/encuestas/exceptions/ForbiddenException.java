package com.igsi.encuestas.exceptions;

// Cuando el usuario no tiene permisos
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) { super(message); }
}