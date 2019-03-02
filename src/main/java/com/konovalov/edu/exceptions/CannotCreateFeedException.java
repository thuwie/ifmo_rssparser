package com.konovalov.edu.exceptions;


/**
 * The type Cannot create feed exception.
 */
public class CannotCreateFeedException  extends RuntimeException {
    /**
     * Instantiates a new Cannot create feed exception.
     */
    public CannotCreateFeedException() {
    }

    /**
     * Instantiates a new Cannot create feed exception.
     *
     * @param message the message
     */
    public CannotCreateFeedException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Cannot create feed exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public CannotCreateFeedException(String message, Throwable cause) {
        super(message, cause);
    }
}
