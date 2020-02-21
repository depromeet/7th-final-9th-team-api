package com.depromeet.todo.domain.task;

import java.util.List;

public enum TaskState {
    TODO, DONE;

    boolean isTodo() {
        return this == TaskState.TODO;
    }

    boolean equals(TaskState taskState) {
        return this == taskState;
    }

    public long getTotalCount(List<Task> tasks) {
        return tasks.stream()
             .filter(task -> this.equals(task.getState()))
             .count();
    }
}
