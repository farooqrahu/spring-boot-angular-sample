package me.vitblokhin.backend.exception;

public class ItemAlreadyExistsException extends RuntimeException {
    public ItemAlreadyExistsException() {
    }

    public ItemAlreadyExistsException(String message) {
        super(message);
    }
} // class ItemAlreadyExistsException
