package com.example.consumption.exception;

public abstract class AbstractException extends RuntimeException {

    /**
     * Presents the URL path of the given request.
     */
    private final String path;

    public AbstractException(String message) {
        super(message);
        this.path = null;
    }

    public AbstractException(String message, String path) {
        super(message);
        this.path = path;
    }

    public AbstractException(String message, Throwable cause) {
        super(message, cause);
        this.path = null;
    }

    public AbstractException(Throwable cause) {
        super(cause);
        this.path = null;
    }

    public String getPath() {
        return path;
    }

}
