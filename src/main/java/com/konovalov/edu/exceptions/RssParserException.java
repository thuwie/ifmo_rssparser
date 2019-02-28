package com.konovalov.edu.exceptions;

public class RssParserException extends RuntimeException {
    public RssParserException(String message) {
        super(message);
    }

    public RssParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public RssParserException(Throwable cause) {
        super(cause);
    }
}
