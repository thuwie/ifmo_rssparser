package com.konovalov.edu.exceptions;

public class NoSuchFeedException extends RuntimeException {
    public NoSuchFeedException() {
    }

    public NoSuchFeedException(String message) {
        super(message);
    }

    public NoSuchFeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchFeedException(Throwable cause) {
        super(cause);
    }
}
