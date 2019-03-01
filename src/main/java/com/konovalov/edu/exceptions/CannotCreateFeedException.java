package com.konovalov.edu.exceptions;

public class CannotCreateFeedException  extends RuntimeException {
    public CannotCreateFeedException() {
    }

    public CannotCreateFeedException(String message) {
        super(message);
    }

    public CannotCreateFeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCreateFeedException(Throwable cause) {
        super(cause);
    }
}
