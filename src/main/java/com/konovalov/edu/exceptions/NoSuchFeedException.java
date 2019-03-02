package com.konovalov.edu.exceptions;

/**
 * The type No such feed exception.
 */
public class NoSuchFeedException extends RuntimeException {
    /**
     * Instantiates a new No such feed exception.
     */
    public NoSuchFeedException() {
    }

    /**
     * Instantiates a new No such feed exception.
     *
     * @param message the message
     */
    public NoSuchFeedException(String message) {
        super(message);
    }

    /**
     * Instantiates a new No such feed exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NoSuchFeedException(String message, Throwable cause) {
        super(message, cause);
    }
}
