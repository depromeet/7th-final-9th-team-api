package com.depromeet.todo.application.tasks;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.task.Task;

public interface DisplayableTaskAssembler {

    Displayable toDisplayableTask(Task task);
}
