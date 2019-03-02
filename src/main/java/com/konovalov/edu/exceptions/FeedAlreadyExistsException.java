package com.konovalov.edu.exceptions;

/**
 * The type Feed already exists exception.
 */
public class FeedAlreadyExistsException extends RuntimeException {
    /**
     * Instantiates a new Feed already exists exception.
     */
    public FeedAlreadyExistsException() {
    }

    /**
     * Instantiates a new Feed already exists exception.
     *
     * @param message the message
     */
    public FeedAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Feed already exists exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FeedAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
