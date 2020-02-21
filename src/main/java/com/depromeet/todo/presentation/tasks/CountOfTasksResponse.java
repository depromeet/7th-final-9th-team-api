package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.Displayable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
class CountOfTasksResponse implements Displayable {

    private long total;
    private long countOfTodo;
    private long countOfDone;
}