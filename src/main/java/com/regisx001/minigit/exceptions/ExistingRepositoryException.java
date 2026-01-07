package com.regisx001.minigit.exceptions;

public class ExistingRepositoryException extends RuntimeException {
    public ExistingRepositoryException(String message) {
        super(message);
    }
}
