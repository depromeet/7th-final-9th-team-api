package com.depromeet.todo.domain.room;

public class IllegalRoomTypeException extends IllegalArgumentException {
    private static final long serialVersionUID = 9211350875305602744L;

    IllegalRoomTypeException(String message) {
        super(message);
    }
}
