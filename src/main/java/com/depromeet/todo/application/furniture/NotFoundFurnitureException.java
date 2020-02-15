package com.depromeet.todo.application.furniture;

import com.depromeet.todo.application.ResourceNotFoundException;

class NotFoundFurnitureException extends ResourceNotFoundException {

    private static final long serialVersionUID = -8880410218886009596L;

    private static final String ERROR_MESSAGE = "가구를 찾을 수 없습니다.(입력값: %l)";

    NotFoundFurnitureException(Long furnitureId) {
        super(String.format(ERROR_MESSAGE, furnitureId));
    }
}
