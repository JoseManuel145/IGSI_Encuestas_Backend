package com.igsi.encuestas.exceptions;

// Base para entidades no encontradas
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}