package com.konovalov.edu.exceptions;

/**
 * The type Rss parser exception.
 */
public class RssParserException extends RuntimeException {
    /**
     * Instantiates a new Rss parser exception.
     *
     * @param message the message
     */
    public RssParserException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Rss parser exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public RssParserException(String message, Throwable cause) {
        super(message, cause);
    }

}
