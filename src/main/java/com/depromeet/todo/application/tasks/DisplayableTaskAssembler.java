package com.depromeet.todo.application.tasks;

import com.depromeet.todo.application.Displayable;
import com.depromeet.todo.domain.task.Tasks;

public interface DisplayableTaskAssembler {

    Displayable toDisplayableTask(Tasks task);
}
