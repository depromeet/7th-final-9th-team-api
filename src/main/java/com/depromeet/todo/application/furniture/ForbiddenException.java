package com.depromeet.todo.application.furniture;

class ForbiddenException extends IllegalStateException {

    private static final long serialVersionUID = -8015901846974824599L;

    private static final String ERROR_MESSAGE = "권한이 없습니다";

    ForbiddenException() {
        super(ERROR_MESSAGE);
    }
}
