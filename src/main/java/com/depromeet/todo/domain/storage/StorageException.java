package com.depromeet.todo.domain.storage;

public class StorageException extends RuntimeException {
    private static final long serialVersionUID = -4520283667938948604L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
