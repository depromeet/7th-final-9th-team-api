package com.depromeet.todo.domain.room;

public class RoomTypeException extends IllegalArgumentException {
    private static final long serialVersionUID = 9211350875305602744L;

    RoomTypeException(String message) {
        super(message);
    }
}
