package com.igsi.encuestas.exceptions;

// Cuando la petición no cumple reglas de negocio
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}