package com.depromeet.todo.application;

import org.springframework.lang.Nullable;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public static void nonNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new BadRequestException(message);
        }
    }
}
