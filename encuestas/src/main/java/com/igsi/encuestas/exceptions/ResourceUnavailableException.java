package com.igsi.encuestas.exceptions;

// Cuando un recurso está inactivo o no disponible temporalmente
public class ResourceUnavailableException extends RuntimeException {
    public ResourceUnavailableException(String message) { super(message); }
}