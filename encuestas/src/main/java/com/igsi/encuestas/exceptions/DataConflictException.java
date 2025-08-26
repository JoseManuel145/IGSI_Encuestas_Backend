package com.igsi.encuestas.exceptions;

// Cuando se intenta crear un recurso que ya existe (violación de integridad)
public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) { super(message); }
}