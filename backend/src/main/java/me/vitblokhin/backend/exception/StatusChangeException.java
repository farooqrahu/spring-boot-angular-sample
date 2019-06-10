package me.vitblokhin.backend.exception;

public class StatusChangeException extends RuntimeException {
    public StatusChangeException() {
    }

    public StatusChangeException(String message) {
        super(message);
    }
} // class StatusChangeException
