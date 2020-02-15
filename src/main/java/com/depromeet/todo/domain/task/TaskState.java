package com.depromeet.todo.domain.task;

public enum TaskState {
    TODO, DONE;

    boolean isTodo() {
        return this == TaskState.TODO;
    }
}
