package com.konovalov.edu.exceptions;

public class FeedAlreadyExistsException extends RuntimeException {
    public FeedAlreadyExistsException() {
    }

    public FeedAlreadyExistsException(String message) {
        super(message);
    }

    public FeedAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
