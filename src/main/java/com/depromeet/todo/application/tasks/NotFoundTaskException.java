package com.depromeet.todo.application.tasks;

public class NotFoundTaskException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "할일이 존재하지 않습니다.(id: %d}";

    NotFoundTaskException(Long taskId) {
        super(String.format(ERROR_MESSAGE, taskId));
    }
}
