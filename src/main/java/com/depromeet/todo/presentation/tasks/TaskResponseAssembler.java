package com.depromeet.todo.presentation.tasks;

import com.depromeet.todo.application.tasks.DisplayableTaskAssembler;
import com.depromeet.todo.domain.task.Tasks;
import org.springframework.stereotype.Component;

@Component
public class TaskResponseAssembler implements DisplayableTaskAssembler {

    @Override
    public TaskResponse toDisplayableTask(Tasks task) {
        if (task == null) {
            return null;
        }
        return TaskResponse.builder()
                    .id(task.getId())
                    .state(task.getState())
                    .order(task.getOrder())
                    .deadline(task.getDeadline())
                    .createdAt(task.getCreatedAt())
                    .build();
    }
}