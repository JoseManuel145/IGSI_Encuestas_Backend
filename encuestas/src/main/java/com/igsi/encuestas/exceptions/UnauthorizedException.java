package com.igsi.encuestas.exceptions;

// Cuando ocurre un error de autorización
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) { super(message); }
}