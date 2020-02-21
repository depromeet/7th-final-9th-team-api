package com.depromeet.todo.domain.task;

import lombok.Getter;

public class TaskCount {

    private TaskState taskState;
    @Getter
    private long countOfTasks;

    public TaskCount(TaskState taskState, long countOfTasks) {
        this.taskState = taskState;
        this.countOfTasks = countOfTasks;
    }

    boolean equalsTaskState(TaskState taskState) {
        return this.taskState.equals(taskState);
    }
}