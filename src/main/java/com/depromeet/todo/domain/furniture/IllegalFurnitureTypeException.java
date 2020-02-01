package com.depromeet.todo.domain.furniture;

public class IllegalFurnitureTypeException extends IllegalArgumentException {

    private static final long serialVersionUID = 1529943003490225494L;

    public IllegalFurnitureTypeException(String message) {
        super(message);
    }
}
