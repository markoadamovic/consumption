package com.example.consumption.exception;

public class NotFoundException extends AbstractException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String path) {
        super(message, path);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
